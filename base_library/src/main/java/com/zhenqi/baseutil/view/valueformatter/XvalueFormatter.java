package com.zhenqi.baseutil.view.valueformatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29 0029.
 */

public class XvalueFormatter implements IAxisValueFormatter {
    List<String> labs = null;
    String[] labss = null;

    public XvalueFormatter(List<String> labs) {
        this.labs = labs;
    }

    public XvalueFormatter(String[] labss) {
        this.labss = labss;
    }

    @Override
    public String getFormattedValue(float v, AxisBase axisBase) {
        if (labs != null) {
            if ((int) v < labs.size())
                return String.valueOf(labs.get((int) v));
        } else if (labss != null) {
            if ((int) v < labss.length)
                return String.valueOf(labss[(int) v]);
        }
        return "";
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
