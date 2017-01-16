package com.lonsec.project.java;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	
	public static final String Config_File = "./lonsec.properties";
	
	/**
	 * Load all property values from
	 * the ./lonsec.properties file in the base folder ./
	 *
	 */
	public static boolean LoadConfig(){
		try{
		    Properties mainProperties = new Properties();

		    FileInputStream config = new FileInputStream(Config_File);

		    //load all the properties from this file
		    mainProperties.load(config);

		    //close the file handle after loaded
		    config.close();
		    
		    // load into Global ConfigValues entity
		    ConfigValues.fund_filepath = mainProperties.getProperty("bench_filepath", ConfigValues.fund_filepath);
		    ConfigValues.fund_filename = mainProperties.getProperty("fund_filename", ConfigValues.fund_filename);
		    ConfigValues.fund_seperator = mainProperties.getProperty("fund_seperator", ConfigValues.fund_seperator);
		    
		    ConfigValues.bench_filepath = mainProperties.getProperty("bench_filepath", ConfigValues.bench_filepath);
		    ConfigValues.bench_filename = mainProperties.getProperty("bench_filename", ConfigValues.bench_filename);
		    ConfigValues.bench_seperator = mainProperties.getProperty("bench_seperator", ConfigValues.bench_seperator);
		    
		    ConfigValues.return_filepath = mainProperties.getProperty("bench_filepath", ConfigValues.return_filepath);
		    ConfigValues.return_benchfilename = mainProperties.getProperty("return_benchfilename", ConfigValues.return_benchfilename);
		    ConfigValues.benchreturn_seperator = mainProperties.getProperty("benchreturn_seperator", ConfigValues.benchreturn_seperator);
		    ConfigValues.return_fundfilename = mainProperties.getProperty("return_fundfilename",ConfigValues.return_fundfilename);
		    ConfigValues.fundreturn_seperator = mainProperties.getProperty("fundreturn_seperator",ConfigValues.fundreturn_seperator);
		    
		    ConfigValues.excess_under = mainProperties.getProperty("excess_under", ConfigValues.excess_under);
		    ConfigValues.excess_out = mainProperties.getProperty("excess_out", ConfigValues.excess_out);
		    ConfigValues.excess_other = mainProperties.getProperty("excess_other", ConfigValues.excess_other);
			
		    ConfigValues.output_filepath = mainProperties.getProperty("output_filepath",ConfigValues.output_filepath);
		    ConfigValues.output_filename = mainProperties.getProperty("output_filename",ConfigValues.output_filename);
		    ConfigValues.output_seperator = mainProperties.getProperty("output_seperator",ConfigValues.output_seperator);
		    
		    MyLogger.info("Read config fund filepath:"+ConfigValues.fund_filepath);

		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
}
