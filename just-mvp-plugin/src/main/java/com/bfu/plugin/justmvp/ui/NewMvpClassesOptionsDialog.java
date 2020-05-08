package com.bfu.plugin.justmvp.ui;

import com.bfu.plugin.justmvp.core.*;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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


    private void initComponents() {
        /* record origin label color. */
        originLabelMessageForeground = labelMessage.getForeground();

        /* checkBox Contract. */
        checkBoxContract.addChangeListener(e -> options.setGenerateContract(checkBoxContract.isSelected()));
        checkBoxContract.setSelected(true); /* default select. */

        /* checkBox Presenter. */
        checkBoxPresenter.addChangeListener(e -> options.setGeneratePresenter(checkBoxPresenter.isSelected()));
        checkBoxPresenter.setSelected(true); /* default select. */

        /* checkBox Layout. */
        checkBoxLayout.addChangeListener(e -> options.setGenerateLayout(checkBoxLayout.isSelected()));
        checkBoxLayout.setEnabled(checkBoxView.isSelected());
        checkBoxLayout.setSelected(checkBoxView.isSelected()); /* default select. */

        /* radio activity. */
        radioActivity.addChangeListener(e -> {
            if (radioActivity.isSelected()) {
                options.setViewType(ViewType.ACTIVITY);
            }
        });
        radioActivity.setEnabled(checkBoxView.isSelected());
        radioActivity.setSelected(true);

        /* radio fragment. */
        radioFragment.addChangeListener(e -> {
            if (radioFragment.isSelected()) {
                options.setViewType(ViewType.FRAGMENT);
            }
        });
        radioFragment.setEnabled(checkBoxView.isSelected());
        radioFragment.setSelected(false);

        /* radio dialog fragment. */
        radioDialogFragment.addChangeListener(e -> {
            if (radioDialogFragment.isSelected()) {
                options.setViewType(ViewType.DIALOG_FRAGMENT);
            }
        });
        radioDialogFragment.setEnabled(checkBoxView.isSelected());
        radioDialogFragment.setSelected(false);

        /* checkBox view. */
        checkBoxView.addChangeListener(e -> {
            options.setGenerateView(checkBoxView.isSelected());

            /* sub item status. */
            radioActivity.setEnabled(checkBoxView.isSelected());
            radioFragment.setEnabled(checkBoxView.isSelected());
            radioDialogFragment.setEnabled(checkBoxView.isSelected());
            checkBoxLayout.setEnabled(checkBoxView.isSelected());
            checkBoxLayout.setSelected(checkBoxView.isSelected());
        });
        checkBoxView.setSelected(true); /* default select. */

        /* radio kotlin. */
        radioKotlin.addChangeListener(e -> options.setLanguageType(radioKotlin.isSelected() ? LanguageType.KOTLIN : LanguageType.JAVA));
        radioKotlin.setSelected(true); /* default select. */

        /* radio java. */
        radioJava.addChangeListener(e -> options.setLanguageType(radioJava.isSelected() ? LanguageType.JAVA : LanguageType.KOTLIN));
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

    @SuppressWarnings("UseJBColor")
    interface MyColor {
        Color RED = new Color(255, 82, 82);
    }

    /**
     * options change.
     */
    private Unit onOptionsChanged() {
        if (MyUtilsKt.isJavaIdentifier(options.getPrefixName())) {
            buttonOK.setEnabled(true);

            labelMessage.setForeground(originLabelMessageForeground);
            labelMessage.setText("   ");

            final String simpleTriggerPath = context.getSimpleTriggerPath();

            /* label contract. */
            final String contractFileName = options.getPrefixName() + "Contract" + options.getLanguageType().getExt();
            labelContract.setText(options.isGenerateContract() ? contractFileName : "    ");
            labelContract.setToolTipText(options.isGenerateContract() ? simpleTriggerPath + "/" + contractFileName : null);

            /* label presenter. */
            final String presenterFileName = options.getPrefixName() + "Presenter" + options.getLanguageType().getExt();
            labelPresenter.setText(options.isGeneratePresenter() ? presenterFileName : "    ");
            labelPresenter.setToolTipText(options.isGeneratePresenter() ? simpleTriggerPath + "/" + presenterFileName : null);

            /* label view. */
            final String viewFileName = options.getPrefixName() + options.getViewType().getNick() + options.getLanguageType().getExt();
            labelView.setText(options.isGenerateView() ? viewFileName : "    ");
            labelView.setToolTipText(options.isGenerateView() ? simpleTriggerPath + "/" + viewFileName : null);

            /* label layout. */
            final String simpleLayoutDirPath = context.getSimpleLayoutDirPath();
            final String layoutFileName = options.getViewType().getLayoutPrefix() + MyUtilsKt.humpToUnderline(options.getPrefixName()) + ".xml";
            labelLayout.setText(options.isGenerateLayout() ? layoutFileName : "     ");
            labelLayout.setToolTipText(options.isGenerateLayout() ? simpleLayoutDirPath + "/" + layoutFileName : null);

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
