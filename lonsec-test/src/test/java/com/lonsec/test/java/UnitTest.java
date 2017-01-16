package com.lonsec.test.java;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.lonsec.project.java.CommUtil;
import com.lonsec.project.java.csv.CsvUtil;
import com.lonsec.project.java.entity.MonthlyOut;

/**
 * Just an easy sample for unit test
 * @author Tony Liu
 * @version 0.1
 */
public class UnitTest {

	@Test
	public void test_date_format() {
		assertEquals(CommUtil.formatDate("15/01/2017"), "2017-01-15");
	}

	@Test
	public void test_roundDecimal() {
		assertEquals(String.valueOf(CommUtil.roundDecimal(3.1415)), String.valueOf("3.14"));
	}

	@Test
	public void test_validReturnLine() {
		String line = "aaa,bbb,ccc";
		String line2 = "aaa,bbb,ccc,ddd";
		assertTrue(CsvUtil.validReturnLine(line, ","));
		assertFalse(CsvUtil.validReturnLine(line2, ","));
	}

	@Test
	public void test_validBenchLine() {
		String line = "aaa,bbb";
		String line2 = "aaa bbb";
		assertTrue(CsvUtil.validBenchLine(line, ","));
		assertTrue(CsvUtil.validBenchLine(line2, " "));
		assertFalse(CsvUtil.validBenchLine(line2, ","));
	}

	@Test
	public void test_sortDateDesc() {
		List<MonthlyOut> inputlist = new ArrayList<>();
		MonthlyOut outsingle = new MonthlyOut();
		outsingle.setDate("2017-01-15");
		outsingle.setReturns(3.22);
		inputlist.add(outsingle);
		
		outsingle = new MonthlyOut();
		outsingle.setDate("2017-01-16");
		outsingle.setReturns(4.22);
		inputlist.add(outsingle);
		
		outsingle = new MonthlyOut();
		outsingle.setDate("2017-01-17");
		outsingle.setReturns(1.22);
		inputlist.add(outsingle);

		outsingle = new MonthlyOut();
		outsingle.setDate("2017-01-17");
		outsingle.setReturns(2.22);
		inputlist.add(outsingle);
		
		outsingle = new MonthlyOut();
		outsingle.setDate("2017-01-17");
		outsingle.setReturns(-2.22);
		inputlist.add(outsingle);

		Collections.sort(inputlist, MonthlyOut.SecondComparator);
		Collections.sort(inputlist, MonthlyOut.FirstComparator);

		assertEquals(inputlist.size(), 5);
		assertEquals(inputlist.get(0).getDate(), "2017-01-17");
		assertEquals(String.valueOf(inputlist.get(0).getReturns()), String.valueOf(2.22));
		
	}

}
