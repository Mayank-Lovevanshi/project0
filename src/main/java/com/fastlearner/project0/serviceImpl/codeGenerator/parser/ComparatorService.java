package com.fastlearner.project0.serviceImpl.codeGenerator.parser;

import java.util.Objects;

public class ComparatorService
{
    public boolean compare(int a,int b)
    {
        return a==b;
    }
    public boolean compare(String a,String b)
    {
        return Objects.equals(a,b);
    }
    public boolean compare(String[] a,String[] b)
    {
        if(a.length!=b.length) return false;
        for(int i=0;i<a.length;i++)
        {
            if(!compare(a[i],b[i])) return false;
        }
        return true;
    }
    public boolean compare(int[] a,int[] b)
    {
        if(a.length!=b.length) return false;
        for(int i=0;i<a.length;i++)
        {
            if(!compare(a[i],b[i])) return false;
        }
        return true;
    }
    public boolean compare(int[][] a,int[][] b)
    {
        if(a.length!=b.length) return false;
        for(int i=0;i<a.length;i++)
        {
            if(!compare(a[i],b[i])) return false;
        }
    }

}
