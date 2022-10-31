package com.mtp.tools;

import java.util.ArrayList;

public class ByteList extends ArrayList<Integer> {
    @Override
    public Integer get(int index) {
        return index < 0 || index > (this.size()-1) ? 0 : super.get(index);
    }
}
