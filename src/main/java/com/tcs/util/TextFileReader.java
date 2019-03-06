package com.tcs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader {
       public List getOrderId() throws IOException {
    	   List list=new ArrayList();
				String fileName = "test.txt";
				File file = new File(fileName);
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line;
				String[] value=new String[5];
				String[] value2= new String[5];
				int i=0;
				while((line = br.readLine()) != null){
					
				    //process the line
					value[i]=line.substring(0, line.indexOf(",")).trim();
					value2[i]= line.substring(line.indexOf(",")+1, line.length()).trim();
					i++;
				}
				
				for(int j=0;j<value.length;j++) {
					System.out.println(value[j]);
				}
				list.add(value);
				list.add(value2);
				return list;

	}
public static void main(String[] s) throws IOException {
	TextFileReader tfr=new TextFileReader();
	tfr.getOrderId();
}
}
