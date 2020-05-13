package com.bfu.plugin.justmvp.ui;

import com.bfu.plugin.justmvp.core.*;
import com.intellij.openapi.vfs.VirtualFile;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class NewMvpClassesOptionsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox checkBoxContract;
    private JCheckBox checkBoxPresenter;
    private JCheckBox checkBoxLayout;
    private JRadioButton radioActivity;
    private JRadioButton radioFragment;
    private JRadioButton radioDialogFragment;
    private JTextField fieldPrefixName;
    private JRadioButton radioKotlin;
    private JRadioButton radioJava;
    private JLabel labelContract;
    private JLabel labelPresenter;
    private JLabel labelView;
    private JLabel labelLayout;
    private JLabel labelMessage;
    private JCheckBox checkBoxView;

    /**
     * record init label foreground
     */
    private Color originLabelMessageForeground;

    /**
     * ok event callback
     */
    private final Callback callback;

    /**
     * context
     */
    private final ActionEventContext context;

    /**
     * source generate options
     */
    private final GenerateOptions options;


    public NewMvpClassesOptionsDialog(ActionEventContext context, Callback callback) {
        this.context = context;
        this.callback = callback;
        options = new GenerateOptions(this::onOptionsChanged);
        setTitle("Create Mvp Classes");
        setContentPane(contentPane);
        setModal(true);
        /*setResizable(false);*/
        /*setLocationRelativeTo(null);*/
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        /* press ESC to close window. */
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        initComponents();
    }

    /**
     * ItemListenerProcessor of JCheckBox
     */
    private static final class ItemListenerProcessor implements ItemListener {

        @NotNull
        private final OnSelectChangeListener onSelectChangeListener;

        private ItemListenerProcessor(@NotNull OnSelectChangeListener onSelectChangeListener) {
            this.onSelectChangeListener = onSelectChangeListener;
        }

        @Override
        public final void itemStateChanged(ItemEvent e) {
            final int stateChange = e.getStateChange();
            if (ItemEvent.SELECTED == stateChange) {
                onSelectChangeListener.onSelectChange(true);
            } else if (ItemEvent.DESELECTED == stateChange) {
                onSelectChangeListener.onSelectChange(false);
            }
        }

        public interface OnSelectChangeListener {
            void onSelectChange(boolean isSelect);
        }
    }


    // see https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
    private void initComponents() {
        /* record origin label color. */
        originLabelMessageForeground = labelMessage.getForeground();

        /* checkBox Contract. */
        checkBoxContract.addItemListener(new ItemListenerProcessor(options::setGenerateContract));
        checkBoxContract.setSelected(true); /* default select. */

        /* checkBox Presenter. */
        checkBoxPresenter.addItemListener(new ItemListenerProcessor(options::setGeneratePresenter));
        checkBoxPresenter.setSelected(true); /* default select. */

        /* checkBox Layout. */
        checkBoxLayout.addItemListener(new ItemListenerProcessor(options::setGenerateLayout));
        checkBoxLayout.setEnabled(checkBoxView.isSelected());
        checkBoxLayout.setSelected(checkBoxView.isSelected()); /* default select. */

        /* radio activity. */
        radioActivity.addItemListener(new ItemListenerProcessor(isSelect -> {
            if (isSelect) {
                options.setViewType(ViewType.ACTIVITY);
            }
        }));
        radioActivity.setEnabled(checkBoxView.isSelected());
        radioActivity.setSelected(true); /* default selected activity. */

        /* radio fragment. */
        radioFragment.addItemListener(new ItemListenerProcessor(isSelect -> {
            if (isSelect) {
                options.setViewType(ViewType.FRAGMENT);
            }
        }));
        radioFragment.setEnabled(checkBoxView.isSelected());
        radioFragment.setSelected(false);

        /* radio dialog fragment. */
        radioDialogFragment.addItemListener(new ItemListenerProcessor(isSelect -> {
            if (isSelect) {
                options.setViewType(ViewType.DIALOG_FRAGMENT);
            }
        }));
        radioDialogFragment.setEnabled(checkBoxView.isSelected());
        radioDialogFragment.setSelected(false);

        /* checkBox view. */
        checkBoxView.addItemListener(new ItemListenerProcessor(isSelect -> {
            options.setGenerateView(isSelect);

            /* sub item status. */
            radioActivity.setEnabled(isSelect);
            radioFragment.setEnabled(isSelect);
            radioDialogFragment.setEnabled(isSelect);
            checkBoxLayout.setEnabled(isSelect);
            checkBoxLayout.setSelected(isSelect);
        }));
        checkBoxView.setSelected(true); /* default select. */

        /* radio kotlin. */
        radioKotlin.addItemListener(new ItemListenerProcessor(isSelect -> options.setLanguageType(isSelect? LanguageType.KOTLIN : LanguageType.JAVA)));
        radioKotlin.setSelected(true); /* default select. */

        /* radio java. */
        radioJava.addItemListener(new ItemListenerProcessor(isSelect -> options.setLanguageType(isSelect? LanguageType.JAVA : LanguageType.KOTLIN)));
        radioJava.setSelected(false); /* default do not select. */

        /* prefix name input. */
        fieldPrefixName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                options.setPrefixName(fieldPrefixName.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                options.setPrefixName(fieldPrefixName.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        /* button ok. */
        buttonOK.addActionListener(e -> onOK());

        /* button cancel. */
        buttonCancel.addActionListener(e -> onCancel());
    }

    @SuppressWarnings({"UseJBColor", "InspectionUsingGrayColors"})
    interface MyColor {
        Color RED = new Color(255, 82, 82);
        Color GREEN = new Color(76, 175, 80);
        Color GREY = new Color(128, 128, 128);
    }

    /**
     * options change.
     */
    private Unit onOptionsChanged() {
        if (MyUtilsKt.isJavaIdentifier(options.getPrefixName())) {
            buttonOK.setEnabled(true);

            /* label message. */
            labelMessage.setForeground(originLabelMessageForeground);
            labelMessage.setText("   ");

            final String simpleTriggerPath = context.getSimpleTriggerPath();
            final VirtualFile triggerDir = context.getTriggerDir();
            final VirtualFile layoutDir = context.getLayoutDir();

            /* label contract. */
            final String contractFileName = options.getPrefixName() + "Contract" + options.getLanguageType().getExt();
            labelContract.setText(options.isGenerateContract() ? contractFileName : "    ");
            labelContract.setToolTipText(options.isGenerateContract() ? simpleTriggerPath + "/" + contractFileName : null);
            labelContract.setForeground(null != triggerDir.findChild(contractFileName) ? MyColor.GREY : MyColor.GREEN);

            /* label presenter. */
            final String presenterFileName = options.getPrefixName() + "Presenter" + options.getLanguageType().getExt();
            labelPresenter.setText(options.isGeneratePresenter() ? presenterFileName : "    ");
            labelPresenter.setToolTipText(options.isGeneratePresenter() ? simpleTriggerPath + "/" + presenterFileName : null);
            labelPresenter.setForeground(null != triggerDir.findChild(presenterFileName) ? MyColor.GREY : MyColor.GREEN);

            /* label view. */
            final String viewFileName = options.getPrefixName() + options.getViewType().getNick() + options.getLanguageType().getExt();
            labelView.setText(options.isGenerateView() ? viewFileName : "    ");
            labelView.setToolTipText(options.isGenerateView() ? simpleTriggerPath + "/" + viewFileName : null);
            labelView.setForeground(null != triggerDir.findChild(viewFileName) ? MyColor.GREY : MyColor.GREEN);

            /* label layout. */
            final String simpleLayoutDirPath = context.getSimpleLayoutDirPath();
            final String layoutFileName = options.getViewType().getLayoutPrefix() + MyUtilsKt.humpToUnderline(options.getPrefixName()) + ".xml";
            labelLayout.setText(options.isGenerateLayout() ? layoutFileName : "     ");
            labelLayout.setToolTipText(options.isGenerateLayout() ? simpleLayoutDirPath + "/" + layoutFileName : null);
            labelLayout.setForeground(null != layoutDir && null != layoutDir.findChild(layoutFileName) ? MyColor.GREY : MyColor.GREEN);

        } else {
            buttonOK.setEnabled(false);
            final boolean isPrefixNameEmpty = options.getPrefixName() == null || options.getPrefixName().trim().isEmpty();
            labelMessage.setText(isPrefixNameEmpty ? "Please input class name prefix." : "Invalid class name prefix!");
            labelMessage.setForeground(isPrefixNameEmpty ? originLabelMessageForeground : MyColor.RED);
            labelContract.setText("   ");
            labelPresenter.setText("   ");
            labelView.setText("   ");
            labelLayout.setText("    ");
        }
        return Unit.INSTANCE;
    }

    private void onOK() {
        dispose();
        if (null != callback) {
            callback.onReady(options);
        }
    }

    private void onCancel() {
        dispose();
    }

    public static void showDialog(ActionEventContext context, Callback callback) {
        final NewMvpClassesOptionsDialog dialog = new NewMvpClassesOptionsDialog(context, callback);
        dialog.pack();
        /* center screen. */
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation((screenSize.width - dialog.getWidth()) / 2, (screenSize.height - dialog.getHeight()) / 2);
        dialog.setVisible(true);
    }

    @FunctionalInterface
    public interface Callback {

        void onReady(@NotNull GenerateOptions options);

    }


}
