/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on 2010-06-06, 18:22:02
 */

package um.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import um.tree.Example;
import um.tree.ExamplesSet;
import um.tree.Node;
import um.tree.RobotAttribute;
import um.tree.RobotCategory;
import um.tree.TreeBuilder;

/**
 *
 * @author piotrrr
 */
public class MainFrame extends javax.swing.JFrame {

    /** Creates new form MainFrame */
    public MainFrame(Node root) {
        initComponents();

        visualizeTree(root);
    }

    public DefaultMutableTreeNode decisionTreeToTreeNodes(Node n) {
        DefaultMutableTreeNode r = new DefaultMutableTreeNode(n);
        if (n.hasTrueChild()) r.add(decisionTreeToTreeNodes(n.getTrueChild()));
        if (n.hasFalseChild()) r.add(decisionTreeToTreeNodes(n.getFalseChild()));
        return r;
    }

    public void visualizeTree(Node root) {
        DefaultMutableTreeNode r = decisionTreeToTreeNodes(root);
        DefaultTreeModel tm = new DefaultTreeModel(r);
        treeView.setModel(tm);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        treeView = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeView.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(treeView);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
    	
    	ExamplesSet s = new ExamplesSet();


        RobotCategory c1 = new RobotCategory(1, 1);
        RobotCategory c2 = new RobotCategory(2, 2);

        s.addExample(new Example(new RobotAttribute(new double [] {1,2}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {4,4}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {3,2}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {2,7}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {3,2}), c2));
    	
        java.awt.EventQueue.invokeLater(new Runner(TreeBuilder.buildTheTree(s, null)));
    }
    
    public static void showTheTree(Node root) {
    	java.awt.EventQueue.invokeLater(new Runner(root));
    }
    
    static class Runner implements Runnable {

    	Node root = null;
    	
    	public Runner(Node r) {
			root = r;
		}
    	
		@Override
		public void run() {
			new MainFrame(root).setVisible(true);
		}
    	
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTree treeView;
    // End of variables declaration//GEN-END:variables

}
