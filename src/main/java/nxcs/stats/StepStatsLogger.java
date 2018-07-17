package nxcs.stats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StepStatsLogger {

    private int xInterval;
    private int yInterval;
    private ArrayList<StepSnapshot> stepSnapshots = new ArrayList<StepSnapshot>();
    public StepStatsLogger(int xint, int yint) {
        this.xInterval = xint;
        this.yInterval = yint;
    }

    public void add(ArrayList<StepSnapshot> stats) {
        stepSnapshots.addAll(stats);
    }


    public void writeLogAndCSVFiles(String csvFile, String logFile) throws IOException {
//
//        SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyyMMddHH");
//        String date_to_string = dateformatyyyyMMdd.format(new Date());
        File csv = new File(csvFile.replaceAll("<TRIAL_NUM>", "step_log"));

        FileWriter dataWriter = null;
        if(!csv.exists()) {
            dataWriter = new FileWriter(csv);
            csv.getParentFile().mkdirs();
            // Write Column Headers
            dataWriter.write("experiment_num, timestamp, weight, obj_r1, p, Q_steps_left, Q_steps_right, Q_steps_delta, Q_steps_min,Q_finalreward_left, Q_finalreward_right, Q_finalreward_delta, Q_finalreward_max" + "\n");
        }
        else
        {
            dataWriter = new FileWriter(csv, true);
        }
        for (StepSnapshot s : this.stepSnapshots) {
            dataWriter.append(s.toCSV_PA());
        }
        dataWriter.close();
    }

    public void writeLogAndCSVFiles_TESTING(String csvFile, String logFile) throws IOException {
//
//        SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyyMMddHH");
//        String date_to_string = dateformatyyyyMMdd.format(new Date());
        File csv = new File(csvFile.replaceAll("<TRIAL_NUM>", "step_log"));

        FileWriter dataWriter = null;
        if (!csv.exists()) {
            dataWriter = new FileWriter(csv);
            csv.getParentFile().mkdirs();
            // Write Column Headers
            dataWriter.write("TrailNumber,Timestamp,TargetWeight,TraceWeight,obj_r1,OpenState,FinalState" +
//                    ",Q_finalreward_left,Q_finalreward_right,Q_finalreward_delta,Q_finalreward_max" +
//                    ",Q_steps_left,Q_steps_right,Q_steps_delta,Q_steps_min" +
//                    ",Q_total_left, Q_total_right,Q_finalreward_select,Q_steps_select,Q_total_select" +
                    ",steps,hyperVolumn,path" +
                    ",PA1[0],PA1[1],PA1[2],PA1[3],PA2[0],PA2[1],PA2[2],PA2[3],PAtotal[0],PAtotal[1],PAtotal[2],PAtotal[3]" +
                    "\n");
        } else {
            dataWriter = new FileWriter(csv, true);
        }
        for (StepSnapshot s : this.stepSnapshots) {
            dataWriter.append(s.to_Total_CSV_PA());
        }
        this.stepSnapshots.clear();
        dataWriter.close();
    }
}
