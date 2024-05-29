package IntegratedMainPackagesSCFS;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestWindowBuilder1 {

    private JFrame frame;
    private JTextField processCountField;
    private List<JTextField> processIDFields;
    private List<JTextField> arriveTimeFields;
    private List<JTextField> burstTimeFields;
    private JLabel resultLabel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TestWindowBuilder1 window = new TestWindowBuilder1();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public TestWindowBuilder1() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    
    //
    //173라인에서 값을 입력 받은 것을 바로 아래 메소들로 넘길건지, scheduling알고리즘을 거쳐서 아래 메소드로 넘길건지 알아보기.
    //차트 그래프에서 필요한 값은 cpu time의 총합 즉, 각각 프로세스에서의 cput burst time이 필요하다.
    //모든 process 의 cpuBurstTime의 합을 Date의 인자로 넘겨줘야지만 간트차트의 x축이 올바르게 나타난다.
    public IntervalCategoryDataset createDataset() {
		TaskSeries series = new TaskSeries("Used Total CPU Time");
	    series.add(new Task("P1", new SimpleTimePeriod(0, 2)));
	    series.add(new Task("P2", new SimpleTimePeriod(2, 4)));
	    series.add(new Task("P3", new SimpleTimePeriod(4, 7)));
	    series.add(new Task("P4", new SimpleTimePeriod(7, 10)));
	    series.add(new Task("P5", new SimpleTimePeriod(10, 13)));
		
		TaskSeriesCollection dataset = new TaskSeriesCollection();
		dataset.add(series);
		return dataset;
	}
    
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1195, 766);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblProcessCount = new JLabel("Number of Processes:");
        lblProcessCount.setBounds(10, 11, 150, 14);
        frame.getContentPane().add(lblProcessCount);

        processCountField = new JTextField();
        processCountField.setBounds(160, 8, 50, 20);
        frame.getContentPane().add(processCountField);
        processCountField.setColumns(10);

        JButton btnSubmitCount = new JButton("Submit Count");
        btnSubmitCount.setBounds(220, 7, 150, 23);
        btnSubmitCount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int processCount = Integer.parseInt(processCountField.getText());
                createProcessFields(processCount);
            }
        });
        frame.getContentPane().add(btnSubmitCount);

        resultLabel = new JLabel("");//결과값들을 나타내느 공간 선언 초기화, 컨탠트팬에 컴포넌트 붙이기
        resultLabel.setBounds(459, 577, 495, 130);
        frame.getContentPane().add(resultLabel);
        
        JPanel panel = new JPanel();
        panel.setBounds(459, 71, 710, 466);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        JFreeChart chart = ChartFactory.createGanttChart("Chart Example", "Each Process", "X axis: time",
    			createDataset());//차트 제목,x축,y축,차트에 대입할 메소드 
    		//차트 y축의 모든 시간대 표시
    		CategoryPlot plot = chart.getCategoryPlot();
    		DateAxis axis = (DateAxis) plot.getRangeAxis();
    		axis.setMaximumDate(new Date(14));
    		axis.setDateFormatOverride(new SimpleDateFormat("SSSS"));
    		//
    		ChartPanel chartPanel = new ChartPanel(chart);
    		chartPanel.setBounds(10, 10, 689, 374);
    		panel.add(chartPanel);
    }

    private void createProcessFields(int processCount) {//프로세스 수가 변경될 때마다 중복이나 남는 것을 피하기 위해서 제거한다.
        if (processIDFields != null) {
            for (JTextField field : processIDFields) {
                frame.getContentPane().remove(field);
            }
            for (JTextField field : arriveTimeFields) {
                frame.getContentPane().remove(field);
            }
            for (JTextField field : burstTimeFields) {
                frame.getContentPane().remove(field);
            }
        }

        processIDFields = new ArrayList<>();//몇 개의 공간이 필요할지 모르므로 각각 ArrayList형태로 만듬.
        arriveTimeFields = new ArrayList<>();
        burstTimeFields = new ArrayList<>();

        for (int i = 0; i < processCount; i++) {//Process의 갯수 만큼의 입력 칸 선언 및 초기화, 컨탠트팬에 컴포넌트를 붙이기
            JLabel lblProcessID = new JLabel("Process ID " + (i + 1) + ":");
            lblProcessID.setBounds(10, 40 + i * 30, 80, 14);
            frame.getContentPane().add(lblProcessID);

            JTextField processIDField = new JTextField();
            processIDField.setBounds(100, 40 + i * 30, 50, 20);
            frame.getContentPane().add(processIDField);
            processIDFields.add(processIDField);

            JLabel lblArriveTime = new JLabel("Arrive Time " + (i + 1) + ":");
            lblArriveTime.setBounds(160, 40 + i * 30, 80, 14);
            frame.getContentPane().add(lblArriveTime);

            JTextField arriveTimeField = new JTextField();
            arriveTimeField.setBounds(250, 40 + i * 30, 50, 20);
            frame.getContentPane().add(arriveTimeField);
            arriveTimeFields.add(arriveTimeField);

            JLabel lblBurstTime = new JLabel("Burst Time " + (i + 1) + ":");
            lblBurstTime.setBounds(310, 40 + i * 30, 80, 14);
            frame.getContentPane().add(lblBurstTime);

            JTextField burstTimeField = new JTextField();
            burstTimeField.setBounds(400, 40 + i * 30, 50, 20);
            frame.getContentPane().add(burstTimeField);
            burstTimeFields.add(burstTimeField);
        }

        JButton btnSubmitProcesses = new JButton("Submit Processes");//Process의 갯수를 입력하고 버튼을 누를 떄에 대한 코드
        btnSubmitProcesses.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Process> jobList = new ArrayList<>();
                for (int i = 0; i < processIDFields.size(); i++) {//입력 받은 값들을 전부 정수 형태로 바꾸고, 이를 Scheduling을 위해서 jobList에 추가한다.
                    int processID = Integer.parseInt(processIDFields.get(i).getText());
                    int arriveTime = Integer.parseInt(arriveTimeFields.get(i).getText());
                    int burstTime = Integer.parseInt(burstTimeFields.get(i).getText());
                    jobList.add(new Process(processID, arriveTime, burstTime));
                }
                List<Result> resultList = new ArrayList<>();
                resultList = SchedulingAlgorithm.run(jobList, resultList);

                StringBuilder resultText = new StringBuilder();
                for (Result result : resultList) {
                    resultText.append(result.toString()).append("\n");
                }
                resultLabel.setText("<html>" + resultText.toString().replaceAll("\n", "<br>") + "</html>");
            }
        });
        btnSubmitProcesses.setBounds(10, 40 + processCount * 30, 176, 23);
        frame.getContentPane().add(btnSubmitProcesses);

        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
}
