package ru.allexs82.utils

object EncodingUtils {

    /**
     * Encodes a list of values into a bitwise mask.
     *
     * @param listOfValues The list of values to encode.
     * @param fullList The full list of values to compare against.
     * @return An integer representing the encoded values.
     */
    fun encodeList(listOfValues: List<Any>, fullList: List<Any>): Int {
        val indexMap = fullList.withIndex().associate { it.value to it.index }
        var mask = 0
        for (item in listOfValues) {
            val index = indexMap[item] ?: error("Item $item not found in fullList")
            mask = mask or (1 shl index)
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
        val decodedList = mutableListOf<E>()
        for ((index, item) in fullList.withIndex()) {
            if ((mask and (1 shl index)) != 0) {
                decodedList.add(item)
            }
        }
        return decodedList
    }
}