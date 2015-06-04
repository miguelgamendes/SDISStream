/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streem;

public class util
{
    /**
     * Copy array one to array two
     * @param one Array to be copied
     * @param two Array copy
     * @return the length of the copied array
     */
    public static int arrayCopy1(byte[] one, byte[] two)
    {
        for (int i = 0; i<two.length; i++)
        {
            two[i] = one[i];
        }
        return two.length;
    }
    
    public static int arrayCopy2(byte[] one, byte[] two)
    {
        for (int i = 0; i<one.length; i++)
        {
            two[i] = one[i];
        }
        return one.length;
    }
    
    /**
     * Returns the legth of an array
     * @param array Array
     * @return Lenght of the array
     */
    public static int getLenght(byte[] array)
    {
        int i=array.length-1;
        while (array[i]== 0x0)
            i--;
        return i;
    }
}
