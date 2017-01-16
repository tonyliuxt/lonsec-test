package com.lonsec.project.java.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import com.lonsec.project.java.CommUtil;
import com.lonsec.project.java.ConfigValues;
import com.lonsec.project.java.MyLogger;
import com.lonsec.project.java.entity.BenchMark;
import com.lonsec.project.java.entity.Fund;
import com.lonsec.project.java.entity.MonthlyOut;
import com.lonsec.project.java.entity.ReturnSeries;

/**
 * Load CSV file into memory with:
 * Validation, Configuration
 * @author TonyLiu
 * @version 0.1
 */
public class CsvUtil {
	
	/**
	 * Load CSV file into memory: suppose the memory size is enough.
	 * @param filePath
	 * @return
	 */
	public static List<String> LoadCsvFile(String filePath){
		List<String> retList = new ArrayList<String>();
		if(filePath == null || filePath.trim().length() == 0){
			return retList;
		}
		
		try{
			Scanner scanner = new Scanner(new File(filePath));
			int count = 0;
			// skip the first line;
			scanner.nextLine();
	        while (scanner.hasNext()) {
	            String line = scanner.nextLine();
	            if(line != null && line.trim().length() != 0){
	            	retList.add(line);
	            	MyLogger.info("line:"+line);
	            }
	            count ++;
	        }
	        MyLogger.info("load total count:" + count +" from file:"+ filePath);
	        scanner.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retList;
	}
	
	/**
	 * LoadFundCsv
	 * @param filePath
	 * @return
	 */
	public static Hashtable<String,Fund> LoadFundCsv(String filePath, String seperator){
		Hashtable<String,Fund> rethash = new Hashtable<String,Fund>();
		List<String> origList = LoadCsvFile(filePath);
		if(origList != null && origList.size() > 0){
			Fund fund = new Fund();
			for(String orig: origList){
				if(validFundLine(orig, seperator)){
					String[] origary = orig.split(seperator);
					fund = new Fund();
					fund.setFundCode(origary[0]);
					fund.setFundName(origary[1]);
					fund.setBenchMarkCode(origary[2]);
					rethash.put(origary[0], fund);
				}
			}
		}
		return rethash;
	}
	
	/**
	 * LoadBenchMarkCsv
	 * @param filePath
	 * @return
	 */
	public static Hashtable<String, BenchMark> LoadBenchMarkCsv(String filePath, String seperator){
		Hashtable<String, BenchMark> retList = new Hashtable<String, BenchMark>();
		List<String> origList = LoadCsvFile(filePath);
		if(origList != null && origList.size() > 0){
			BenchMark bench = new BenchMark();
			for(String orig: origList){
				if(validBenchLine(orig, seperator)){
					String[] origary = orig.split(seperator);
					bench = new BenchMark();
					bench.setBenchMarkCode(origary[0]);
					bench.setBenchMarkCode(origary[1]);
					retList.put(origary[0], bench);
				}
			}
		}
		return retList;
	}
	
	/**
	 * LoadReturnCsv
	 * @param filePath
	 * @return
	 */
	public static Hashtable<String, ReturnSeries> LoadFundReturnCsv(String filePath, String seperator){
		Hashtable<String, ReturnSeries> retList = new Hashtable<String, ReturnSeries>();
		List<String> origList = LoadCsvFile(filePath);
		if(origList != null && origList.size() > 0){
			ReturnSeries retseries = new ReturnSeries();
			for(String orig: origList){
				if(validReturnLine(orig, seperator)){
					String[] origary = orig.split(seperator);
					retseries = new ReturnSeries();
					retseries.setCode(origary[0]);
					retseries.setDate(CommUtil.formatDate(origary[1]));
					retseries.setReturns(Double.parseDouble(origary[2]));
					retList.put(origary[0]+ConfigValues.output_seperator+CommUtil.formatDate(origary[1]), retseries);
				}
			}
		}
		return retList;
	}

	/**
	 * LoadReturnCsv
	 * @param filePath
	 * @return
	 */
	public static Hashtable<String, ReturnSeries> LoadBenchReturnCsv(String filePath, String seperator){
		Hashtable<String, ReturnSeries> retList = new Hashtable<String, ReturnSeries>();
		List<String> origList = LoadCsvFile(filePath);
		if(origList != null && origList.size() > 0){
			ReturnSeries retseries = new ReturnSeries();
			for(String orig: origList){
				if(validReturnLine(orig, seperator)){
					String[] origary = orig.split(seperator);
					retseries = new ReturnSeries();
					retseries.setCode(origary[0]);
					retseries.setDate(origary[1]);
					retseries.setReturns(Double.parseDouble(origary[2]));
					retList.put(origary[0]+ConfigValues.output_seperator+origary[1], retseries);
				}
			}
		}
		return retList;
	}

	/**
	 * Write file
	 * Suppose the disk size is enough for io operation
	 * @param csvFile
	 */
    public static void WriteOutPut(String csvFile, List<MonthlyOut> inputlist, String seperator){
    	if(inputlist == null || inputlist.size() == 0){
    		MyLogger.info("WriteOutPut error input list.");
    		return;
    	}
    	try{
            FileWriter writer = new FileWriter(csvFile);
            //For header
            writeLine(writer, Arrays.asList("FundName", "Date", "Excess", "OutPerformance", "Return", "Rank"), seperator);
            //For contents
            int rank = 0;
            String date = inputlist.get(0).getDate();
            for (MonthlyOut mout : inputlist) {
            	
            	// Group by date and rank with highest return
            	if(! ( date.equals(mout.getDate()))){
            		rank = 0;
            		date = mout.getDate();
            	}
            	
            	rank ++;
                List<String> list = new ArrayList<String>();
                list.add(mout.getFundName());
                list.add(mout.getDate().toString());
                list.add(String.valueOf(mout.getExcess()));
                list.add(String.valueOf(mout.getOutPerformance()));
                list.add(String.valueOf(mout.getReturns()));
                list.add(String.valueOf(rank));
                writeLine(writer, list, seperator);
            }
            writer.flush();
            writer.close();
            MyLogger.info("WriteOutPut total lines:" + inputlist.size());

    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    /**
     * suppose there are no "\"" need to deal
     * @param w
     * @param values
     * @throws IOException
     */
    public static void writeLine(Writer w, List<String> values, String seperator) throws IOException {

        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(seperator);
            }
            sb.append(value);
            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }

	/**
	 * Basic validation
	 * @param line
	 * @return
	 */
	public static boolean validCsvLine(String line){
		if(line != null && line.trim().length() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Validation for Fund
	 * @param line
	 * @return
	 */
	public static boolean validFundLine(String line, String seperator){
		if(validCsvLine(line)){
			if(line.split(seperator).length == 3){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Validation for Bench
	 * @param line
	 * @return
	 */
	public static boolean validBenchLine(String line, String seperator){
		if(validCsvLine(line)){
			if(line.split(seperator).length == 2){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Validation for Returns
	 * @param line
	 * @return
	 */
	public static boolean validReturnLine(String line, String seperator){
		if(validCsvLine(line)){
			if(line.split(seperator).length == 3){
				return true;
			}
		}
		return false;
	}

}
