package com.fastlearner.project0.serviceImpl.judge;

import org.springframework.stereotype.Service;

@Service
public class EvaluateJudge0Response
{
    public int getPassedTestCases(String expectedOutput,String actualOutput)
    {
        if(actualOutput==null) return 0;
        String[] expectedOutputArray = expectedOutput.split("\n");
        String[] actualOutputArray = actualOutput.split("\n");
        int n = Math.min(expectedOutputArray.length,actualOutputArray.length);
        int passedTestCases = 0;
        for(int i=0;i<n;i++)
        {
            if(normalize(expectedOutputArray[i]).equals(normalize(actualOutputArray[i]))) passedTestCases++;
            else break;
        }
        return passedTestCases;
    }
    private String normalize(String s)
    {
        return s
                .replace("\r\n", "\n")
                .replace("\r", "\n")
                .trim();
    }
}
