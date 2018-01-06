package neu.lab.view.tab.scatter;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import neu.lab.risk.RiskGraphData;

public class ChartTab extends JPanel {
	public static String[] types = { "dir_lm", "acs" };
	private ChartPanel chartP;
	private JList<String> typeList;

	public ChartTab() {
		typeList = new JList<String>(types);
		initScatterChart();
		initHistograph();
		this.add(chartP, BorderLayout.CENTER);
		this.add(typeList, BorderLayout.WEST);
	}

	private void initHistograph() {
		// TODO Auto-generated method stub
		
	}

	private void initScatterChart() {
		// 获取数据
		DefaultXYDataset xydataset = new DefaultXYDataset();
		xydataset.addSeries("dir_lm", RiskGraphData.riskDataMap.get("dir_lm").getScatterData());
		// 初始化chart
		JFreeChart localJFreeChart = ChartFactory.createScatterPlot("Scatter Plot Demo 3", "X", "Y", xydataset,
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot localXYPlot = (XYPlot) localJFreeChart.getPlot();
		localXYPlot.setDomainCrosshairVisible(true);
		localXYPlot.setDomainCrosshairLockedOnData(true);
		localXYPlot.setRangeCrosshairVisible(true);
		localXYPlot.setRangeCrosshairLockedOnData(true);
		localXYPlot.setDomainZeroBaselineVisible(true);
		localXYPlot.setRangeZeroBaselineVisible(true);
		localXYPlot.setDomainPannable(true);
		localXYPlot.setRangePannable(true);
		NumberAxis localNumberAxis = (NumberAxis) localXYPlot.getDomainAxis();
		localNumberAxis.setAutoRangeIncludesZero(false);
		// 初始化panel
		chartP = new ChartPanel(localJFreeChart);
		chartP.setMouseWheelEnabled(true);
	}
}
