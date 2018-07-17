library(dplyr)
library(stringr)
library(ggplot2)


############ settings ##############
#maze4 <- c('maze4_path_weight100_weight30.csv','Maze4 - 201801162151 - Trial 0 - TRIAL_NUM - 6000 - TEST.csv') #Maze4 - 201801162151 - Trial 0 - TRIAL_NUM - 6000 - TEST.csv')
maze4 <- c('maze4_path_weight100_weight30.csv','nxcs.testbed.maze4_weighted_sum - 201801222157 - Trial 0 - TRIAL_NUM - 6000 - TEST.csv')
maze5 <- c('maze5_path_weight100_weight30.csv','maze5 - 201801222158 - Trial 0 - TRIAL_NUM - 6000 - TEST.csv') #Maze5 - 201801162151 - Trial 0 - TRIAL_NUM - 6000 - TEST.csv')
maze6 <- c('maze6_path_weight100_weight30.csv','maze6 - 201801222201 - Trial 0 - TRIAL_NUM - 6000 - TEST.csv') #Maze6 - 201801151104 - Trial 0 - TRIAL_NUM - 6000 - TEST.csv.csv')


upperBound <- 6000
# traceWeightFilter <- c('0.040000|0.960000'
#                        , '0.480000|0.520000'
#                        #, '0.520000|0.480000'
#                        , '0.960000|0.040000') #c('0.000000|1.000000', '0.560000|0.440000', '1.000000|0.000000')
# 
# plot.labels <- list(expression(paste(lambda[1],'=0.04, 0.96','  ',sep=''))
#                     , expression(paste(lambda[2],'=0.48, 0.52','  ',sep=''))
#                     #, expression(paste(lambda[3],'=0.52, 0.48',sep=''))
#                     , expression(paste(lambda[3],'=0.96, 0.04','  ',sep='')) )


traceWeightFilter <- c('0.000000|1.000000' ,
                       '0.111111|0.888889' ,
                       '0.222222|0.777778' ,
                       '0.333333|0.666667' ,
                       '0.444444|0.555556' ,
                       '0.555556|0.444444' ,
                       '0.666667|0.333333' ,
                       '0.777778|0.222222' ,
                       '0.888889|0.111111' ,
                       '1.000000|0.000000'
                       )

plot.upperBound <- 6000
plot.traceWeightFilter <- c(#'0.000000|1.000000' ,
                       #'0.111111|0.888889' ,
                       '0.222222|0.777778' ,
                       #'0.333333|0.666667' ,
                       '0.444444|0.555556' ,
                       '0.555556|0.444444' ,
                       #'0.666667|0.333333' ,
                       '0.777778|0.222222' 
                       #'0.888889|0.111111' ,
                       #'1.000000|0.000000'
                       )

plot.labels <- list(#expression(paste(lambda[0],'=0.0, 1.0','  ',sep=''))
                    #, expression(paste(lambda[1],'=0.11, 0.89','  ',sep=''))
                     expression(paste(lambda[2],'=0.22, 0.78','  ',sep=''))
                    #, expression(paste(lambda[3],'=0.33, 0.67','  ',sep=''))
                    , expression(paste(lambda[4],'=0.44, 0.56','  ',sep=''))
                    , expression(paste(lambda[5],'=0.56, 0.44','  ',sep=''))
                    #, expression(paste(lambda[6],'=0.67, 0.33','  ',sep=''))
                    , expression(paste(lambda[7],'=0.78, 0.22','  ',sep=''))
                    #, expression(paste(lambda[8],'=0.89, 0.11','  ',sep=''))
                    #, expression(paste(lambda[9],'=1.0, 0.0','  ',sep='')) 
                    )


##################
mazeToRun <- maze6





############# begin to read result #############
setwd("C:/Users/martin.xie/IdeaProjects/XCS_MOEAD/dataAnalysis/Maze")

targetSteps <- read.csv(file = mazeToRun[1], header = TRUE, sep = ",", stringsAsFactors = FALSE)
targetId <- paste(targetSteps$open, targetSteps$final, paste(as.character(targetSteps$step), '', sep = ''), sep = '*')
targetSteps <- cbind(targetSteps, targetId)


setwd("C:/Users/martin.xie/IdeaProjects/XCS_MOEAD/log/MOXCS")
raw.data <- read.csv(file =   mazeToRun[2] #Train - 201801141417 - Trial 0 - TRIAL_NUM - 6000 - TEST.csv.csv"
                     , header = TRUE, sep = ","
                     , stringsAsFactors = FALSE
                     , row.names=NULL)

ax <- colnames(raw.data)

raw.data$PAtotal3 <- NULL

names(raw.data) <- ax[-1]


data <- raw.data %>% 
    select(TrailNumber, Timestamp, TargetWeight, TraceWeight, obj_r1, OpenState, FinalState, steps, hyperVolumn, path) %>%
    filter(TraceWeight %in% traceWeightFilter
          , Timestamp <= upperBound)

## release memory
rm(raw.data)

################ check if uid in final state pair ###############
uid <- paste(data$OpenState, data$FinalState, data$steps, sep = "*")
data <- cbind(data, uid)
data$match <- ifelse(data$uid %in% targetSteps$targetId, 1, 0)
rm(uid)

################ calculate match rate ###############
result <- data %>%
            group_by(TrailNumber, Timestamp,TargetWeight,TraceWeight ) %>%
            summarise(groupRow = n()
                    , matchCount = sum(match)
                    , matchRate =matchCount/groupRow 
                    , hyperVolumn = mean(hyperVolumn))

uniqTrail <- unique(result$TrailNumber)
pall <- rep(NULL, nrow(uniqTrail))
pdata <- NULL

for (i in uniqTrail) {
    pdata <- result %>%
        filter(TrailNumber == i
        #, TraceWeight == '5.000000|5.000000'
        #, TraceWeight == uniqWeight[i] #' 0.000000|1.000000'
        )
    ggplot(pdata, aes(x = Timestamp, y = matchRate, group = TraceWeight, color = TraceWeight, linetype = TraceWeight) )+
      geom_line() +
      labs(title = paste('Trail', i,sep=' ')) 
}

#ggplot(pdata, aes(x = Timestamp, y = matchRate, group = TraceWeight, color = c('#41ae76', '#ef6548', '#4292c6'))) +
#    geom_line()



################ calculate mean match rate and hyper volume ###############
retdata <- result %>%
  group_by(Timestamp, TargetWeight, TraceWeight) %>%
  summarise(matchRate = mean(matchRate) 
          , hyperVolumn = mean(hyperVolumn))


plt <- ggplot(retdata, aes(x = Timestamp, y = matchRate, group = TraceWeight, color = TraceWeight, linetype = TraceWeight)) +
    geom_line()



########### plot begin ###########
theme_set(theme_classic(base_size = 9))


lty = c(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
lshp = c(1, 2, 3, 4, 5, 6, 7, 8, 9,10)
cbbPalette = c('#e41a1c', '#377eb8', '#4daf4a'
               , '#984ea3', '#ff7f00', '#66ff66'
               , '#a65628', '#f781bf', '#000000'
               ,'#f781bf')


################ plot data ###############
plot.data <- retdata %>% filter(TraceWeight  %in% plot.traceWeightFilter
                                , Timestamp <= plot.upperBound
                                , TargetWeight %in% c('30.000000|100.000000')
            )

################ plot hyper volume ###############
phv <- ggplot(data = plot.data, aes(
  x = Timestamp,
  y = hyperVolumn,
  colour = TraceWeight,
  group = TraceWeight,
  linetype = TraceWeight
  )) +
  geom_line() +
  labs(x = 'Number of Leaning Problems\n(a)', y = NULL) +
  ggtitle("THV") +
  theme(axis.title.y = element_text(size = rel(1.1), face = "bold"), axis.title.x = element_text(size = rel(1.1), face = "bold"), title = element_text(size = rel(1.1), face = 'bold')) +
  theme(legend.text = element_text(size = rel(1), face = "bold")) +
  theme(legend.title = element_blank()) +
  #theme(legend.position = c(0.63, 0.15))
theme(legend.position = 'bottom') + theme(panel.grid.major = element_line(size = 0.01, linetype = 'dotted',
                                                                           colour = "black"),
                                           panel.grid.minor = element_line(size = 0.001, linetype = 'dotted',
                                                                           colour = "black")) +
  theme(legend.background = element_rect(fill = alpha('gray', 0.05))) +
  theme(axis.text.x = element_text(size = rel(1.4)),
        axis.text.y = element_text(size = rel(1.4)),
        axis.line.x = element_line(size = rel(0.4),colour = 'black',linetype = 'solid'),
        axis.line.y = element_line(size = rel(0.4),colour = 'black',linetype = 'solid'),
        axis.title = element_text(size = rel(1.2), face = "bold")) +
  scale_linetype_manual(values = lty, guide = "none") +
  scale_colour_manual(values = cbbPalette, labels = plot.labels) +
  guides(colour=guide_legend(override.aes=list(linetype=1:length(plot.traceWeightFilter))))


################ plot match rate ###############
pmr <- ggplot(data = plot.data, aes(
  x = Timestamp,
  y = matchRate,
  colour = TraceWeight,
  group = TraceWeight,
  linetype = TraceWeight)) +
  geom_line() +
  labs(x = 'Number of Leaning Problems\n(b)', y = NULL) +
  ggtitle("% OP") +
  theme(axis.title.y = element_text(size = rel(1.1), face = "bold"), axis.title.x = element_text(size = rel(1.1), face = "bold"), title = element_text(size = rel(1.1), face = 'bold')) +
  theme(legend.text = element_text(size = rel(1), face = "bold")) +
  theme(legend.title = element_blank()) +
  #theme(legend.position = c(0.63, 0.15))
theme(legend.position = 'bottom') + theme(panel.grid.major = element_line(size = 0.01, linetype = 'dotted',
                                                                           colour = "black"),
                                           panel.grid.minor = element_line(size = 0.001, linetype = 'dotted',
                                                                           colour = "black")) +
  theme(legend.background = element_rect(fill = alpha('gray', 0.05))) +
  theme(axis.text.x = element_text(size = rel(1.4)),
        axis.text.y = element_text(size = rel(1.4)),
        axis.line.x = element_line(size = rel(0.4),colour = 'black',linetype = 'solid'),
        axis.line.y = element_line(size = rel(0.4),colour = 'black',linetype = 'solid'),
        axis.title = element_text(size = rel(1.2), face = "bold")) +
  scale_linetype_manual(values = lty) +
  scale_colour_manual(values = cbbPalette)


################ plot arrange plots into one ###############
library(gridExtra)

g_legend <- function(a.gplot) {
    tmp <- ggplot_gtable(ggplot_build(a.gplot))
    leg <- which(sapply(tmp$grobs, function(x) x$name) == "guide-box")
    legend <- tmp$grobs[[leg]]
    return(legend)
}

mylegend <- g_legend(phv)

p3 <- grid.arrange(arrangeGrob(phv + theme(legend.position = "none"),
                               pmr + theme(legend.position = "none"),
                               nrow = 1),
                   mylegend, nrow = 2, heights = c(5, 1) )

p3