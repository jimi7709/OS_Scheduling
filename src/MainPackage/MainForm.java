package MainPackage;

//Form1.cs + Form1.Designer.cs
import javax.swing.JFrame;

public class MainForm extends JFrame {

    public MainForm() {
        initializeComponent();
    }

    private void initializeComponent() {
        // Placeholder for form initialization code
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new MainForm().setVisible(true);
        });
    }
}
