package eu.kprod.gui.changepanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import eu.kprod.ds.MwDataModel;
import eu.kprod.gui.comp.MwJCheckBox;
import eu.kprod.gui.comp.MwJLabel;
import eu.kprod.gui.comp.MwJPanel;

public class MwBOXPanel extends MwChangeablePanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MwBOXPanel(String name) {
        super(name);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub
        final Object source = e.getSource();
        if (source instanceof MwDataModel) {

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {

                    MwDataModel m = (MwDataModel) source;
                    removeAll();
                    setLayout(new GridLayout(1, 1));
                    add(build(m.getBOXs(), m.getBoxNameIndex()));
                    revalidate();

                }

            });

        }
    }

    private Component build(Map<String, List<Boolean>> map,
            Map<Integer, String> index) {
        MwJPanel mainPane = new MwJPanel();
        mainPane.setLayout(new GridLayout(
                1 + (index == null ? 0 : index.size()), 1));
        MwJPanel pane = new MwJPanel();

        mainPane.add(pane);

        if (map == null || index == null) {
            pane.setLayout(new GridLayout(1, 1));
            pane.add(new MwJLabel("AUX - EMPTY", Color.RED));
            mainPane.add(pane);
            return mainPane;
        }

        pane.setLayout(new GridLayout(1, 5));
        pane.add(new MwJLabel());
        pane.add(new MwJLabel("aux1"));
        pane.add(new MwJLabel("aux2"));
        pane.add(new MwJLabel("aux3"));
        pane.add(new MwJLabel("aux4"));
        for (int i = 0; i < index.size(); i++) {
            String name = index.get(i);
            pane = new MwJPanel();
            pane.setLayout(new GridLayout(1, 5));

            pane.add(new JLabel(name));

            List<Boolean> BoxItem = map.get(name);
            int j = 0;
            int auxCnt = 0;
            MwJPanel auxPane = new MwJPanel();
            auxPane.setLayout(new GridLayout(1, 3));
            for (Boolean state : BoxItem) {

                // TODO get step and bound from msp
                JCheckBox chck = new MwJCheckBox(name, j, "aux" + (auxCnt + 1));
                chck.setSelected(state);
                auxPane.add(chck);
                j++;
                if (j == 3) {
                    pane.add(auxPane);
                    auxPane = new MwJPanel();
                    auxPane.setLayout(new GridLayout(1, 3));
                    j = 0;
                    auxCnt++;
                }
            }
            mainPane.add(pane);
        }
        return mainPane;
    }

}
