package ru.allexs82.kotlin.utils

object EncodeUtils {

    /**
     * Encodes a list of values into a bitwise mask.
     *
     * @param listOfValues The list of values to encode.
     * @param fullList The full list of values to compare against.
     * @return An integer representing the encoded values.
     */
    fun encodeList(listOfValues: List<Any>, fullList: List<Any>): Int {
        var mask = 0
        for (item in listOfValues) {
            mask = mask or (1 shl fullList.indexOf(item))
        }
        return mask
    }

    /**
     * Decodes a bitwise mask to retrieve the list of values.
     *
     * @param mask The integer representing the encoded values.
     * @param fullList The full list of values to compare against.
     * @return A list of values that are represented by the mask.
     */
    fun <E> decodeList(mask: Int, fullList: List<E>): MutableList<E> {
        val result: MutableList<E> = mutableListOf()
        for (item in fullList) {
            if ((mask and (1 shl fullList.indexOf(item))) != 0) {
                result.add(item)
            }
        }
        return result
    }
}