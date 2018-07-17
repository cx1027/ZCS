package nxcs.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MazeParameters {

    public int totalTrailCount;
    public int finalStateUpperBound;
    public int resultInterval;
    public int numOfChartBars;
    public String logFolder;
    public String mazeFile;
    public String rewardFile;
    public boolean logLowerFinalState;
    public int method;

    public String getFileTimestampFormat() {
        return fileTimestampFormat;
    }

    public String fileTimestampFormat;

    public MazeParameters() {

        SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyyMMddHHmm");
        this.fileTimestampFormat = dateformatyyyyMMdd.format(new Date());
        this.resultInterval = 600;
        this.numOfChartBars = 20;
        //0:pointMatch, 1:stateMatch, 2:bothMatch, 3:oneMatch
        this.method = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=======MazeParameters=======");
        sb.append("\tfinalStateUpperBound:" + this.finalStateUpperBound);
        sb.append("\tresultInterval:" + this.resultInterval);
        sb.append("\ttotalTrailCount:" + this.totalTrailCount);
        sb.append("\tfileTimestampFormat:" + this.fileTimestampFormat);
        sb.append("\tlogFolder:" + this.logFolder);
        return sb.toString();
    }

}
