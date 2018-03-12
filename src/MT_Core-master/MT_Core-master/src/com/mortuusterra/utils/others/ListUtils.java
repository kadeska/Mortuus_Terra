/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.utils.others;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    /**
     * Util method to divide a large list into several smaller ones
     * @param list The list in question
     * @param size the size of the returned list. The last one will be smaller if not exactly divisible
     * @return List of smaller lists.
     */
    public static <T> List<List<T>> subList(List<T> list, int size){
        List<List<T>> lists = new ArrayList<>();
        for (int i = size; i < list.size(); i += size) {
            if (i > list.size()) {
                break;
            }

            int up = Math.min(list.size(), i += size);
            i-= size;

            lists.add(list.subList(i, up));
        }

        return lists;
    }
}
