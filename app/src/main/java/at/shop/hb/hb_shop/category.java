package at.shop.hb.hb_shop;

import java.util.Arrays;

/**
 * Created by Stefan on 10.07.15.
 */
public class category
{
    public int id;
    public String main;
    public String[] sub;

    public category(String items[])
    {
        main = items[0];
        sub = Arrays.copyOfRange(items,1,items.length);
    }

}
