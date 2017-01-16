package com.lonsec.project.java;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import com.lonsec.project.java.csv.CsvUtil;
import com.lonsec.project.java.entity.BenchMark;
import com.lonsec.project.java.entity.Fund;
import com.lonsec.project.java.entity.MonthlyOut;
import com.lonsec.project.java.entity.ReturnSeries;

/**
 * Primary process logic Task [Could be a timer task] 
 * @author Tony Liu
 * @version 0.1
 */
public class ProcessTask extends TimerTask{

    public ProcessTask()
    {
    }
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		scanAndProcess();
	}
    
	private void scanAndProcess(){
		// Start to generate output result...
		List<MonthlyOut> inputlist = new ArrayList<>();

		// 1. load fund
		Hashtable<String, Fund> fundHash = CsvUtil.LoadFundCsv(ConfigValues.fund_filepath + ConfigValues.fund_filename, ConfigValues.fund_seperator);
		if(fundHash == null || fundHash.size() == 0){
			return;
		}
		
		// 2. load bench 
		Hashtable<String, BenchMark> benchHash = CsvUtil.LoadBenchMarkCsv(ConfigValues.bench_filepath + ConfigValues.bench_filename, ConfigValues.bench_seperator);
		if(benchHash == null || benchHash.size() == 0){
			return;
		}
		
		// 3. load fund return
		Hashtable<String, ReturnSeries> fundreturnHash = CsvUtil.LoadFundReturnCsv(ConfigValues.return_filepath + ConfigValues.return_fundfilename, ConfigValues.fundreturn_seperator);
		if(fundreturnHash == null || fundreturnHash.size() == 0){
			return;
		}
		
		// 4. load bench return
		Hashtable<String, ReturnSeries> benchreturnHash = CsvUtil.LoadBenchReturnCsv(ConfigValues.return_filepath + ConfigValues.return_benchfilename, ConfigValues.benchreturn_seperator);
		if(benchreturnHash == null || benchreturnHash.size() == 0){
			return;
		}
		
		// A. start Application Algorithm 
		if(fundreturnHash != null && fundreturnHash.size() > 0){
			Set<String> keys = fundreturnHash.keySet();
			Iterator<String> itr = keys.iterator();
			while (itr.hasNext()) {
				
				// find fund return
				String key = itr.next();
				ReturnSeries value = fundreturnHash.get(key);
				if(value == null){
					continue;
				}
				
				// find fund list
				String[] realkey = key.split(ConfigValues.output_seperator);
				if(realkey == null || realkey.length != 2){
					continue;
				}
				
				Fund searchOne = fundHash.get(realkey[0]);
				if(searchOne == null){
					continue;
				}
				
				// find benchreturn
				String benchkey = searchOne.getBenchMarkCode() +ConfigValues.output_seperator+ value.getDate();
				ReturnSeries benchRet = benchreturnHash.get(benchkey);
				if(benchRet == null){
					continue;
				}
				
				// calculator excess
				double excess = CommUtil.roundDecimal(value.getReturns() - benchRet.getReturns());
				
				MonthlyOut outsingle = new MonthlyOut();
				outsingle.setFundName(searchOne.getFundName());
				outsingle.setDate(value.getDate());
				outsingle.setExcess(excess);
				outsingle.setReturns(CommUtil.roundDecimal(value.getReturns()));
				outsingle.setOutPerformance(getPerformance(excess));
				
				inputlist.add(outsingle);
		    } 
		}
		if(inputlist == null || inputlist.size() == 0){
			return;
		}
		
		// Sorted with [date] and [rank-return]
		Collections.sort(inputlist, MonthlyOut.SecondComparator);
		Collections.sort(inputlist, MonthlyOut.FirstComparator);
		
		// 5. write the result
		CsvUtil.WriteOutPut(ConfigValues.output_filepath + ConfigValues.output_filename, inputlist, ConfigValues.output_seperator);
	}
	
	/**
	 * return performance based on configuration
	 * @param value
	 * @return
	 */
	private String getPerformance(double excess){
		String[] under = ConfigValues.excess_under.split(ConfigValues.EXCESS_SPLIT);
		if(under == null || under.length != 2){
			MyLogger.info("ConfigValues.excess_under Error parameter.");
			return "";
		}
		String[] out = ConfigValues.excess_out.split(ConfigValues.EXCESS_SPLIT);
		if(out == null || out.length != 2){
			MyLogger.info("ConfigValues.excess_out Error parameter.");
			return "";
		}
		try{
			double undervalue = Double.parseDouble(under[0]);
			double outvalue = Double.parseDouble(out[0]);
			
			if(excess > outvalue){
				return out[1];
			}else if(excess < undervalue){
				return under[1];
			}else{
				return ConfigValues.excess_other;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return "";
		}
	}
}
