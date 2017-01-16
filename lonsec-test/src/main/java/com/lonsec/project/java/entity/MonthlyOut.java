package com.lonsec.project.java.entity;

import java.util.Comparator;

public class MonthlyOut implements Comparable<MonthlyOut> {
	
	private String fundName;
	private String date;
	private double excess;
	private String outPerformance;
	private double returns;
	private int rank;
	
	public MonthlyOut() {
		super();
	}
	
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getExcess() {
		return excess;
	}
	public void setExcess(double excess) {
		this.excess = excess;
	}
	public String getOutPerformance() {
		return outPerformance;
	}
	public void setOutPerformance(String outPerformance) {
		this.outPerformance = outPerformance;
	}
	public double getReturns() {
		return returns;
	}
	public void setReturns(double returns) {
		this.returns = returns;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	@Override
	public int compareTo(MonthlyOut out) {
		// TODO Auto-generated method stub
		// sorted by [Date] descending
		return out.getDate().compareTo(this.getDate());
	}
	
    /**
     * sorted by [Date] descending
     */
    public static Comparator<MonthlyOut> FirstComparator = new Comparator<MonthlyOut>() {

        @Override
        public int compare(MonthlyOut e1, MonthlyOut e2) {
            return e2.getDate().compareTo(e1.getDate());
        }
    };

    /**
     * sorted by [Return-Rank] ascending
     */
    public static Comparator<MonthlyOut> SecondComparator = new Comparator<MonthlyOut>() {

        @Override
        public int compare(MonthlyOut e1, MonthlyOut e2) {
            return (int) (e2.getReturns()*1000 - e1.getReturns()*1000);
        }
    };
}
