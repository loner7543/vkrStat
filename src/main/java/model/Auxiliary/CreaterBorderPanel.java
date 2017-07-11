
package model.Auxiliary;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class CreaterBorderPanel {

    public static final int EMPTY_BORDER=1;
    public static final int ETCHED_BORDER=2;

    Border etched = BorderFactory.createEmptyBorder();

    public static JPanel createBorderPanel(JComponent panel, String title, int style)
    {
        JPanel p=new JPanel();
        p.setLayout(new BorderLayout());
        Border etched;
        if(style==2) etched = BorderFactory.createEtchedBorder();
        else etched = BorderFactory.createEmptyBorder();
        Border titled = BorderFactory.createTitledBorder(etched, title);
        p.setBorder(titled);
        p.add(panel,BorderLayout.CENTER);
        return p;
    }
    public static JPanel createBorderPanel(String title, int style)
    {
        JPanel p=new JPanel();
        p.setLayout(new BorderLayout());
        Border etched;
        if(style==2) etched = BorderFactory.createEtchedBorder();
        else etched = BorderFactory.createEmptyBorder();
        Border titled = BorderFactory.createTitledBorder(etched, title);
        p.setBorder(titled);
        return p;
    }
}
