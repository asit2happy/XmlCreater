package com.tcs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextFileReader {
       public String[] getOrderId() throws IOException {
				String fileName = "test.txt";
				File file = new File(fileName);
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line;
				String[] value=new String[5];
				int i=0;
				while((line = br.readLine()) != null){
					
				    //process the line
					value[i]=line.substring(0, line.indexOf(","));
					i++;
				}
				
				for(int j=0;j<value.length;j++) {
					System.out.println(value[j]);
				}
				return value;

	}
public static void main(String[] s) throws IOException {
	TextFileReader tfr=new TextFileReader();
	tfr.getOrderId();
}
}
