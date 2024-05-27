package IntegratedMainPackagesSCFS;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
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
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 500);
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
        btnSubmitCount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int processCount = Integer.parseInt(processCountField.getText());
                createProcessFields(processCount);
            }
        });
        btnSubmitCount.setBounds(220, 7, 150, 23);
        frame.getContentPane().add(btnSubmitCount);

        resultLabel = new JLabel("");
        resultLabel.setBounds(10, 320, 450, 130);
        frame.getContentPane().add(resultLabel);
    }

    private void createProcessFields(int processCount) {
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

        processIDFields = new ArrayList<>();
        arriveTimeFields = new ArrayList<>();
        burstTimeFields = new ArrayList<>();

        for (int i = 0; i < processCount; i++) {
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

        JButton btnSubmitProcesses = new JButton("Submit Processes");
        btnSubmitProcesses.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Process> jobList = new ArrayList<>();
                for (int i = 0; i < processIDFields.size(); i++) {
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
