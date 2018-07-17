package nxcs.common;

import nxcs.Classifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {


    public ArrayList<String> replace(String target) {
        return replaceHelper(target, target.length() - 1);
    }

    public ArrayList<String> replaceHelper(String target, int to) {
        char c = target.charAt(to);
        if (to == 0) {
            ArrayList<String> res = new ArrayList<String>();
            if (c == '#') {
                res.add("0");
                res.add("1");
            } else {
                res.add(c + "");
            }
            return res;
        }
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<String> preRes = replaceHelper(target, to - 1);
        if (c == '#') {
            for (String token : preRes) {
                res.add(token + "0");
                res.add(token + "1");
            }
        } else {
            for (String token : preRes) {
                res.add(token + c);
            }
        }
        return res;
    }


}
