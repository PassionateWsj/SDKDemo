package com.intfocus.syptemplatev1.utils

import com.intfocus.syptemplatev1.entity.BargraphComparator
import java.util.Comparator

/**
 * Created by liuruilin on 2017/9/25.
 */
class BargraphDataComparator : Comparator<BargraphComparator> {

    override fun compare(obj1: BargraphComparator, obj2: BargraphComparator): Int {
        val v1 = obj1.data.toFloat()
        val v2 = obj2.data.toFloat()
        return if (v1 > v2) {
            1
        } else if (v1 < v2) {
            -1
        } else {
            0
        }
    }
}
