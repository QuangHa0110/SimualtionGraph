package Final;

import java.util.*;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

public class View extends JFrame {
	List<List<String>> listPath = new ArrayList<List<String>>();
	private JPanel contentPane, panel, panel_1;
	private String begin;
	private JTextField textField;
	private int countStep = 0; // đếm số bước hiện tại
	private int maxStep = 0;// số bước tối đa do người dùng nhập vào
	private DataInput dataInput;

	private Graphic demo = new Graphic();
	private JTextField jTextBeginPoint;
	private JTextField jTextEndPoint;
	private JTextField jTextCurrentPath;
	private String currentPath;

	// Fix
	private String cPath = "";
	// End
	public List<String> listPathToEndNode = new ArrayList<String>();
	private BFS bfs;

	// Fix
	private ArrayList<List<Integer>> countStepTwoNode = new ArrayList<List<Integer>>();

	// End
	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					View frame = new View();

					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame.
	 */
	public View() {
		JButton btnNewButton = new JButton("Quay lại");
		JButton btnNewButton_3 = new JButton("Đến đích");
		JButton btnNewButton_1 = new JButton("Đi tiếp");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1433, 817);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("NHÓM 6 - 124158");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(490, 10, 347, 37);
		contentPane.add(lblNewLabel);

		panel = new JPanel(new GridLayout());
		panel.setBackground(Color.WHITE);
		panel.setBounds(606, 65, 791, 637);
		contentPane.add(panel);
		panel.setVisible(false);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(49, 100, 310, 102);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		panel_2.setVisible(false);

		JLabel jLabelBeginPoint = new JLabel("Đỉnh bắt đầu");
		jLabelBeginPoint.setBounds(10, 20, 134, 26);
		panel_2.add(jLabelBeginPoint);
		jLabelBeginPoint.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBeginPoint.setFont(new Font("Tahoma", Font.BOLD, 14));

		jTextBeginPoint = new JTextField();
		jTextBeginPoint.setFont(new Font("Tahoma", Font.BOLD, 12));
		jTextBeginPoint.setBounds(166, 23, 96, 26);
		panel_2.add(jTextBeginPoint);
		jTextBeginPoint.setHorizontalAlignment(SwingConstants.CENTER);
		jTextBeginPoint.setColumns(10);

		JLabel jLabelEndPoint = new JLabel("Đỉnh kết thúc");
		jLabelEndPoint.setBounds(10, 56, 139, 26);
		panel_2.add(jLabelEndPoint);
		jLabelEndPoint.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelEndPoint.setFont(new Font("Tahoma", Font.BOLD, 14));

		jTextEndPoint = new JTextField();
		jTextEndPoint.setFont(new Font("Tahoma", Font.BOLD, 12));
		jTextEndPoint.setBounds(166, 59, 96, 26);
		panel_2.add(jTextEndPoint);
		jTextEndPoint.setHorizontalAlignment(SwingConstants.CENTER);
		jTextEndPoint.setColumns(10);
		currentPath = jTextBeginPoint.getText();

		panel_1 = new JPanel();
		panel_1.setBounds(25, 258, 537, 353);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		panel_1.setVisible(false);

		JLabel numberCurrentStep = new JLabel("0");
		numberCurrentStep.setBounds(210, 55, 62, 23);
		panel_1.add(numberCurrentStep);
		numberCurrentStep.setHorizontalAlignment(SwingConstants.CENTER);
		numberCurrentStep.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel CurrentNode = new JLabel("");
		CurrentNode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		CurrentNode.setBounds(175, 200, 45, 35);
		panel_1.add(CurrentNode);
		CurrentNode.setHorizontalAlignment(SwingConstants.CENTER);

		JList b = new JList();
		b.setFont(new Font("Tahoma", Font.PLAIN, 14));
		b.setSize(52, 85);
		b.setLocation(196, 105);
		b.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel_1.add(b);
		JScrollPane jScrollPane = new JScrollPane(b);
		jScrollPane.setSize(50, 85);
		jScrollPane.setLocation(184, 105);
		panel_1.add(jScrollPane);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(210, 10, 96, 35);
		panel_1.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Nhập vào số bước tối đa : \r\n");
		lblNewLabel_5.setBounds(0, 12, 200, 26);
		panel_1.add(lblNewLabel_5);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setLabelFor(this);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblNewLabel_6 = new JLabel("Số bước đã đi");
		lblNewLabel_6.setBounds(21, 48, 132, 37);
		panel_1.add(lblNewLabel_6);
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel jLaberCurrentPath = new JLabel("Đường đi của bạn");
		jLaberCurrentPath.setBounds(25, 626, 166, 35);
		contentPane.add(jLaberCurrentPath);
		jLaberCurrentPath.setHorizontalAlignment(SwingConstants.CENTER);
		jLaberCurrentPath.setFont(new Font("Tahoma", Font.BOLD, 14));
		jLaberCurrentPath.setVisible(false);

		jTextCurrentPath = new JTextField();
		jTextCurrentPath.setFont(new Font("Tahoma", Font.BOLD, 14));
		// jTextCurrentPath.setBounds(25, 671, 537, 86);
		contentPane.add(jTextCurrentPath);
		// jTextCurrentPath.setColumns(10);
		jTextCurrentPath.setVisible(false);
		JScrollPane jScrollPane2 = new JScrollPane(jTextCurrentPath);
		jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		jScrollPane2.setLocation(25, 671);
		jScrollPane2.setSize(573, 86);
		contentPane.add(jScrollPane2);
		jScrollPane2.setVisible(false);

		// nút bắt đầu trong phần shortest path
		JButton btnShortestPath = new JButton("Bắt đầu");
		btnShortestPath.addMouseListener(new MouseAdapter() {
			@Override

			public void mouseClicked(MouseEvent e) {

				if (validation(jTextBeginPoint, jTextEndPoint, dataInput.getMaxDinh())) {

					demo.setColorDefault();
					panel_1.setVisible(false);

					bfs.bfs(Integer.parseInt(jTextBeginPoint.getText()));
					int beginPoint = Integer.parseInt(jTextEndPoint.getText());
					currentPath = jTextEndPoint.getText();
					boolean check = true;
					int dem = 0;
					while (beginPoint != Integer.parseInt(jTextBeginPoint.getText())) {
						currentPath = bfs.getP()[beginPoint] + "-->" + currentPath;
						demo.setColorEdge(bfs.getP()[beginPoint] + "_" + beginPoint);
						beginPoint = bfs.getP()[beginPoint];
						demo.setColorNode("" + beginPoint);
						dem++;
						if (dem > dataInput.getMaxDinh()) {
							demo.setColorDefault();
							check = false;
							JOptionPane.showMessageDialog(null, "Không có đường đi giữa 2 đỉnh này");
							break;

						}
					}
					if (check == true) {
						demo.setColorNode(jTextEndPoint.getText());
						jTextCurrentPath.setText(currentPath);
					}

				}

			}
		});

		btnShortestPath.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnShortestPath.setBounds(29, 215, 160, 33);
		contentPane.add(btnShortestPath);
		btnShortestPath.setVisible(false);

		// Fix

		// Đến đích
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jTextBeginPoint.disable();
				jTextEndPoint.disable();

				countStep += countStepTwoNode.get(Integer.parseInt(begin))
						.get(-1 + Integer.parseInt(jTextEndPoint.getText()));
				int diemcuoi = Integer.parseInt(jTextEndPoint.getText());
				demo.setColorNode("" + listNode(listPath.get(Integer.parseInt(begin)).get(-1 + diemcuoi)).get(0));
				demo.setColorEdge(
						begin + "_" + listNode(listPath.get(Integer.parseInt(begin)).get(-1 + diemcuoi)).get(0));
				cPath += listPath.get(Integer.parseInt(begin)).get(-1 + Integer.parseInt(jTextEndPoint.getText()));
				for (int i = 0; i < -1
						+ listNode(listPath.get(Integer.parseInt(begin)).get(-1 + diemcuoi)).size(); i++) {
					int a = (int) listNode(listPath.get(Integer.parseInt(begin)).get(-1 + diemcuoi)).get(i);
					int b = (int) listNode(listPath.get(Integer.parseInt(begin)).get(-1 + diemcuoi)).get(i + 1);
					demo.setColorNode(
							"" + listNode(listPath.get(Integer.parseInt(begin)).get(-1 + diemcuoi)).get(i + 1));

					String id = a + "_" + b;

					demo.setColorEdge(id);

				}
				List listNodeBetweenTwoNode = new ArrayList<>(
						listNode(listPath.get(Integer.parseInt(begin)).get(-1 + diemcuoi)));
				for (int i = 0; i < listNodeBetweenTwoNode.size(); i++) {
					currentPath += "-->" + listNodeBetweenTwoNode.get(i);

				}
				jTextCurrentPath.setText(currentPath);
				begin = "" + diemcuoi;
				numberCurrentStep.setText("" + countStep);
				CurrentNode.setText(begin);

				b.setListData(dataInput.getDanhSachDinh().get(diemcuoi).toArray());

				if (listPath.get(Integer.parseInt(begin)).get(-1 + diemcuoi) == "") {
					btnNewButton_3.setEnabled(false);
				} else {
					btnNewButton_3.setEnabled(true);
				}
				if (dataInput.getDanhSachDinh().get(diemcuoi).isEmpty()) {
					btnNewButton_1.setEnabled(false);
				}
			}
		});
		// End

		// nút đi tiếp
		btnNewButton_1.setBounds(123, 265, 79, 40);
		panel_1.add(btnNewButton_1);
		btnNewButton_1.setVisible(true);
		btnNewButton_1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				jTextBeginPoint.disable();
				jTextEndPoint.disable();
				btnNewButton.setEnabled(true);
				if (btnNewButton_1.isEnabled()) {

					int chooseNode = (int) b.getSelectedValue();

					countStep++;

					cPath += chooseNode + " ";

					int countStepFromStartToNow = countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText()))
							.get(-1 + chooseNode);
					int countStepFromNowToEnd = countStepTwoNode.get(chooseNode)
							.get(-1 + Integer.parseInt(jTextEndPoint.getText()));
					int counStepFromStartToEnd = countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText()))
							.get(-1 + Integer.parseInt(jTextEndPoint.getText()));

					// Fix
					if (countStepFromStartToNow != 0) {
						if (countStep < countStepFromStartToNow) {
							// Nếu số bước nhở hơn số bước trước đây đã đi thì cập nhật
							listPath.get(Integer.parseInt(jTextBeginPoint.getText())).set(-1 + chooseNode, cPath);
							countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText())).set(-1 + chooseNode,
									countStep);

						}
					} else {
						listPath.get(Integer.parseInt(jTextBeginPoint.getText())).set(-1 + chooseNode, cPath);
						countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText())).set(-1 + chooseNode,
								countStep);

					}

					// End

					// //Chức năng ghép nối
					for (int i = 1; i <= dataInput.getMaxDinh(); i++) {
						if (countStepTwoNode.get(i).get(-1 + Integer.parseInt(jTextBeginPoint.getText())) > 0) {
							if (countStepTwoNode.get(i).get(-1 + chooseNode) > 0) {
								if (countStepTwoNode.get(i).get(-1 + Integer.parseInt(jTextBeginPoint.getText()))
										+ countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText()))
												.get(-1 + chooseNode) < countStepTwoNode.get(i).get(-1 + chooseNode)) {
									countStepTwoNode.get(i).set(-1 + chooseNode,
											countStepTwoNode.get(i)
													.get(-1 + Integer.parseInt(jTextBeginPoint.getText()))
													+ countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText()))
															.get(-1 + chooseNode));
									listPath.get(i).set(-1 + chooseNode,
											listPath.get(i).get(-1 + Integer.parseInt(jTextBeginPoint.getText()))
													+ listPath.get(Integer.parseInt(jTextBeginPoint.getText()))
															.get(-1 + chooseNode));

								}
							} else {
								countStepTwoNode.get(i).set(-1 + chooseNode,
										countStepTwoNode.get(i).get(-1 + Integer.parseInt(jTextBeginPoint.getText()))
												+ countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText()))
														.get(-1 + chooseNode));
								listPath.get(i).set(-1 + chooseNode,
										listPath.get(i).get(-1 + Integer.parseInt(jTextBeginPoint.getText())) + listPath
												.get(Integer.parseInt(jTextBeginPoint.getText())).get(-1 + chooseNode));

							}
						}
					}

					//
					//
					for (int i = 1; i <= dataInput.getMaxDinh(); i++) {
						if (countStepTwoNode.get(chooseNode).get(-1 + i) > 0) {
							if (countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText())).get(-1 + i) > 0) {
								if (countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText()))
										.get(-1 + chooseNode)
										+ countStepTwoNode.get(chooseNode).get(-1 + i) < countStepTwoNode
												.get(Integer.parseInt(jTextBeginPoint.getText())).get(-1 + i)) {
									countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText()))
											.set(-1 + i,
													countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText()))
															.get(-1 + chooseNode)
															+ countStepTwoNode.get(chooseNode).get(-1 + i));
									listPath.get(Integer.parseInt(jTextBeginPoint.getText())).set(-1 + i,
											listPath.get(Integer.parseInt(jTextBeginPoint.getText()))
													.get(-1 + chooseNode) + listPath.get(chooseNode).get(-1 + i));
									for (int j = 1; j <= dataInput.getMaxDinh(); j++) {
										if (countStepTwoNode.get(j).get(-1 + chooseNode) > 0) {
											if (countStepTwoNode.get(j).get(-1 + i) > 0) {
												if (countStepTwoNode.get(j).get(-1 + chooseNode)
														+ countStepTwoNode.get(chooseNode)
																.get(-1 + i) < countStepTwoNode.get(j).get(-1 + i)) {
													countStepTwoNode.get(j).set(-1 + i,
															countStepTwoNode.get(j).get(-1 + chooseNode)
																	+ countStepTwoNode.get(chooseNode).get(-1 + i));
													listPath.get(j).set(-1 + i, listPath.get(j).get(-1 + chooseNode)
															+ listPath.get(chooseNode).get(-1 + i));

												}
											} else {
												countStepTwoNode.get(j).set(-1 + i,
														countStepTwoNode.get(j).get(-1 + chooseNode)
																+ countStepTwoNode.get(chooseNode).get(-1 + i));
												listPath.get(j).set(-1 + i, listPath.get(j).get(-1 + chooseNode)
														+ listPath.get(chooseNode).get(-1 + i));

											}
										}
									}

								}
							} else {
								countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText()))
										.set(-1 + i,
												countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText()))
														.get(-1 + chooseNode)
														+ countStepTwoNode.get(chooseNode).get(-1 + i));
								listPath.get(Integer.parseInt(jTextBeginPoint.getText())).set(-1 + i,
										listPath.get(Integer.parseInt(jTextBeginPoint.getText())).get(-1 + chooseNode)
												+ listPath.get(chooseNode).get(-1 + i));
								for (int j = 1; j <= dataInput.getMaxDinh(); j++) {
									if (countStepTwoNode.get(j).get(-1 + chooseNode) > 0) {
										if (countStepTwoNode.get(j).get(-1 + i) > 0) {
											if (countStepTwoNode.get(j).get(-1 + chooseNode)
													+ countStepTwoNode.get(chooseNode).get(-1 + i) < countStepTwoNode
															.get(j).get(-1 + i)) {
												countStepTwoNode.get(j).set(-1 + i,
														countStepTwoNode.get(j).get(-1 + chooseNode)
																+ countStepTwoNode.get(chooseNode).get(-1 + i));
												listPath.get(j).set(-1 + i, listPath.get(j).get(-1 + chooseNode)
														+ listPath.get(chooseNode).get(-1 + i));

											}
										} else {
											countStepTwoNode.get(j).set(-1 + i,
													countStepTwoNode.get(j).get(-1 + chooseNode)
															+ countStepTwoNode.get(chooseNode).get(-1 + i));
											listPath.get(j).set(-1 + i, listPath.get(j).get(-1 + chooseNode)
													+ listPath.get(chooseNode).get(-1 + i));

										}
									}
								}

							}
						}
					}
					//
					// countStepFromStartToNow=countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText())).get(-1+chooseNode);
					// if(countStepFromStartToNow>0) {
					// //Nếu đường này đã đi rồi thì vào
					//
					//
					// if(countStepFromNowToEnd >0) {
					// //Nếu có đường từ đỉnh hiện tại đến đích thì vào
					// if( counStepFromStartToEnd>0){
					// //Nếu có đường từ đỉnh đầu đến đỉnh cuối
					// if(countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText())).get(-1+chooseNode)+countStepFromNowToEnd<counStepFromStartToEnd)
					// {
					// // Nếu số bước từ đỉnh đầu đến đỉnh hiện tại cộng số bước trước đây đã đi từ
					// đỉnh hiện tại đến đỉnh cuối nhỏ hơn số bước trước đây đa đi từ đỉnh đầu đến
					// đỉnh cuối thì cập nhật
					//
					// listPath.get(Integer.parseInt(jTextBeginPoint.getText())).set(-1+Integer.parseInt(jTextEndPoint.getText()),listPath.get(Integer.parseInt(jTextBeginPoint.getText())).get(-1+chooseNode)+listPath.get(chooseNode).get(-1+Integer.parseInt(jTextEndPoint.getText())))
					// ;
					// countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText())).set(-1+Integer.parseInt(jTextEndPoint.getText()),countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText())).get(-1+chooseNode)+countStepFromNowToEnd);
					//
					// }
					// }else {
					// //Nếu không có đường đi từ đỉnh đầu đến đỉnh cuối thì cập nhật
					// listPath.get(Integer.parseInt(jTextBeginPoint.getText())).set(-1+Integer.parseInt(jTextEndPoint.getText()),listPath.get(Integer.parseInt(jTextBeginPoint.getText())).get(-1+chooseNode)+listPath.get(chooseNode).get(-1+Integer.parseInt(jTextEndPoint.getText())))
					// ;
					// countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText())).set(-1+Integer.parseInt(jTextEndPoint.getText()),countStepTwoNode.get(Integer.parseInt(jTextBeginPoint.getText())).get(-1+chooseNode)+countStepFromNowToEnd);
					//
					// }
					// }
					// }

					// End

					if (countStep <= maxStep) {

						numberCurrentStep.setText("" + countStep);
						//
						// demo.setColorNode(b.getSelectedValue().toString());
						// String id = begin + "_" + b.getSelectedValue().toString();
						// demo.setColorEdge(id);

						boolean check = true;

						if (!dataInput.getDanhSachDinh().get((int) b.getSelectedValue()).isEmpty()) {

							if (b.getSelectedValue().toString().equals(jTextEndPoint.getText())) {
								JOptionPane.showMessageDialog(null, "Bạn đã đi đến đỉnh cuối cùng");
								if (!isContinute()) {
									check = false;
									btnNewButton_1.setEnabled(false);

								}

							}

							// Fix
							demo.setColorNode("" + chooseNode);
							demo.setColorEdge(begin + "_" + chooseNode);
							// End

							currentPath += "-->" + chooseNode;
							jTextCurrentPath.setText(currentPath);
							begin = b.getSelectedValue().toString();
							b.setListData(dataInput.getDanhSachDinh().get(Integer.parseInt(begin)).toArray());
							b.setSelectedIndex(0);
							CurrentNode.setText(begin);

						} else {

							if (b.getSelectedValue().toString().equals(jTextEndPoint.getText())) {
								JOptionPane.showMessageDialog(null, "Bạn đã đi đến đỉnh cuối cùng");
								if (!isContinute()) {
									check = false;
									btnNewButton_1.setEnabled(false);
									demo.setColorNode("" + chooseNode);
									demo.setColorEdge(begin + "_" + chooseNode);
									currentPath += "-->" + chooseNode;
									jTextCurrentPath.setText(currentPath);
									begin = b.getSelectedValue().toString();
									b.setListData(dataInput.getDanhSachDinh().get(Integer.parseInt(begin)).toArray());
									b.setSelectedIndex(0);
									CurrentNode.setText(begin);
								} else {

									countStep--;
									if (countStep == 0) {
										btnNewButton.setEnabled(false);
									}
									JOptionPane.showMessageDialog(null,
											"Không có đỉnh kề với đỉnh tiếp theo. Chọn lại đỉnh từ đỉnh trước đó");
									// Fix
									cPath = (String) cPath.subSequence(0,
											cPath.length() - 1 - ("" + b.getSelectedValue()).length());
									b.setListData(dataInput.getDanhSachDinh().get(Integer.parseInt(begin)).toArray());
									b.setSelectedIndex(0);
									jTextCurrentPath.setText(currentPath);
									CurrentNode.setText(begin);
									// End
								}
							} else {
								countStep--;
								if (countStep == 0) {
									btnNewButton.setEnabled(false);
								}
								cPath = cPath.substring(0, cPath.length() - 1 - ("" + b.getSelectedValue()).length());
								numberCurrentStep.setText("" + countStep);
								// Fix
								b.setListData(dataInput.getDanhSachDinh().get(Integer.parseInt(begin)).toArray());
								b.setSelectedIndex(0);
								jTextCurrentPath.setText(currentPath);
								CurrentNode.setText(begin);
								// End

								JOptionPane.showMessageDialog(null,
										"Không có đỉnh kề với đỉnh tiếp theo. Chọn lại đỉnh từ đỉnh trước đó");
							}
						}
						if (check && (countStep
								+ countStepTwoNode.get(Integer.parseInt(begin))
										.get(-1 + Integer.parseInt(jTextEndPoint.getText())) <= maxStep
								&& listPath.get(Integer.parseInt(begin))
										.get(-1 + Integer.parseInt(jTextEndPoint.getText())) != "")) {
							System.out.println(listPath.get(Integer.parseInt(begin))
									.get(-1 + Integer.parseInt(jTextEndPoint.getText())));
							btnNewButton_3.setEnabled(true);

						} else {
							btnNewButton_3.setEnabled(false);
						}
						;
					} else {
						JOptionPane.showMessageDialog(null, "Đã đi hết số bước đưa ra");
					}

				}

			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnNewButton_2 = new JButton("Bắt đầu");
		btnNewButton_2.setBounds(21, 266, 92, 38);
		panel_1.add(btnNewButton_2);

		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblNewLabel_4 = new JLabel("Đỉnh hiện tại :");
		lblNewLabel_4.setBounds(21, 198, 126, 37);
		panel_1.add(lblNewLabel_4);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblNewLabel_1 = new JLabel("Chọn điểm tiếp theo");
		lblNewLabel_1.setBounds(21, 105, 153, 19);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));

		// Quay lại
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(226, 265, 95, 41);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (countStep == 0) {
					btnNewButton.setEnabled(false);
				}
				// Đỉnh hiện tại
				int currentNode2 = Integer.parseInt(begin);
				String stringBegin = "" + begin;
				countStep--;
				numberCurrentStep.setText("" + countStep);
				// FIx
				currentPath = (String) currentPath.subSequence(0, currentPath.length() - 3 - stringBegin.length());
				cPath = (String) cPath.subSequence(0, cPath.length() - 1 - stringBegin.length());
				if (cPath.compareTo("") != 0) {

					List<Integer> listNodePath = listNode(jTextBeginPoint.getText() + " " + cPath);
					boolean checkNode = false;
					for (int i = 0; i < listNodePath.size(); i++) {
						if (Integer.parseInt(begin) == listNodePath.get(i)) {
							checkNode = true;

						}
					}
					if (checkNode == false) {
						demo.setColorNodeDefault(begin);
					}
					if (!(jTextBeginPoint.getText() + " " + cPath)
							.contains((listNodePath.get(-1 + listNodePath.size()) + " " + begin + " "))) {
						demo.setColorEdgeDefault(listNodePath.get(-1 + listNodePath.size()), begin);
					} else {
						if ((jTextBeginPoint.getText() + " " + cPath)
								.indexOf((listNodePath.get(-1 + listNodePath.size()) + " " + begin + " ")) > 0) {
							if (!(jTextBeginPoint.getText() + " " + cPath)
									.contains((" " + listNodePath.get(-1 + listNodePath.size()) + " " + begin + " "))) {
								demo.setColorEdgeDefault(listNodePath.get(-1 + listNodePath.size()), begin);
							}
						}
					}

					begin = "" + listNodePath.get(-1 + listNodePath.size());

					b.setListData(dataInput.getDanhSachDinh().get(Integer.parseInt(begin)).toArray());

					b.setSelectedIndex(0);
					CurrentNode.setText(begin);
					jTextCurrentPath.setText(currentPath);
				} else {

					jTextCurrentPath.setText(currentPath);
					demo.setColorNodeDefault(begin);
					demo.setColorEdgeDefault(Integer.parseInt(jTextBeginPoint.getText()), begin);
					begin = jTextBeginPoint.getText();
					CurrentNode.setText(begin);
					b.setListData(dataInput.getDanhSachDinh().get(Integer.parseInt(begin)).toArray());
					b.setSelectedIndex(0);
					demo.setColorNode(jTextBeginPoint.getText());
					JOptionPane.showMessageDialog(null, "Bạn đã ở đỉnh xuất phát");
					btnNewButton.setEnabled(false);

				}
				btnNewButton_1.setEnabled(true);
				if (listPath.get(Integer.parseInt(begin)).get(-1 + Integer.parseInt(jTextEndPoint.getText())) != "") {
					btnNewButton_3.setEnabled(true);
				}
			}

//						

		});
btnNewButton_3.setEnabled(false);
		// nút bắt đầu
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jTextBeginPoint.enable();
				jTextEndPoint.enable();

				btnNewButton_1.setEnabled(true);
				countStep = 0;
				numberCurrentStep.setText("0");
				demo.setColorDefault();
				cPath = "";
				currentPath = jTextBeginPoint.getText();
				jTextCurrentPath.setText(currentPath);

				if (textField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập số bước đi ");
				} else if (validation(jTextBeginPoint, jTextEndPoint, dataInput.getMaxDinh())) {
					
					String s = textField.getText();
					begin = jTextBeginPoint.getText();
					if(dataInput.getDanhSachDinh().get(Integer.parseInt(begin)).size() <=0) {
						JOptionPane.showMessageDialog(null, "Đỉnh bắt đầu không có được đi đến đỉnh nào khác");
						return;
					}
					demo.setColorNode(begin);
					CurrentNode.setText(begin);
					maxStep = Integer.parseInt(s);
					b.setListData(dataInput.getDanhSachDinh().get(Integer.parseInt(begin)).toArray());
					b.setSelectedIndex(0);
					btnNewButton.setEnabled(false);
				}
				if (jTextBeginPoint.getText().equals(jTextEndPoint.getText()) == true) {
					JOptionPane.showMessageDialog(null, "Bạn đã đi đến đỉnh cuối cùng");
					if (!isContinute()) {

						btnNewButton_1.setEnabled(false);

					} else {
						if (dataInput.getDanhSachDinh().get(Integer.parseInt(jTextEndPoint.getText())).isEmpty()) {
							btnNewButton_1.setEnabled(false);
						}

					}
					btnNewButton.setEnabled(false);
				}
				if (listPath.get(Integer.parseInt(jTextBeginPoint.getText()))
						.get(-1 + Integer.parseInt(jTextEndPoint.getText())) != "") {
					btnNewButton_3.setEnabled(true);
				} else {
					btnNewButton_3.setEnabled(false);
				}

			}

		});
		panel_1.add(btnNewButton);

		btnNewButton_3.setForeground(UIManager.getColor("Button.black"));
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_3.setBounds(389, 16, 118, 25);
		panel_1.add(btnNewButton_3);

		JButton btnNewButton_9 = new JButton();
		ImageIcon iconZoomOut = new ImageIcon("zoom_out.jpg");
		btnNewButton_9.setIcon(iconZoomOut);
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double new_view_percent = demo.getViewPanel().getCamera().getViewPercent() + 0.5;
				demo.getViewPanel().getCamera().setViewPercent(new_view_percent);
			}
		});
		btnNewButton_9.setBounds(670, 712, 45, 45);
		btnNewButton_9.setToolTipText("Thu nhỏ màn hình");
		contentPane.add(btnNewButton_9);
		btnNewButton_9.setVisible(false);

		JButton btnNewButton_5 = new JButton();
		ImageIcon iconZoomIn = new ImageIcon("zoom_in.jpg");
		btnNewButton_5.setIcon(iconZoomIn);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double current_view_percent = demo.getViewPanel().getCamera().getViewPercent() - 0.5;
				demo.getViewPanel().getCamera().setViewPercent(current_view_percent);
			}
		});
		btnNewButton_5.setBounds(601, 712, 45, 45);
		btnNewButton_5.setToolTipText("Phóng to màn hình");
		contentPane.add(btnNewButton_5);
		btnNewButton_5.setVisible(false);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(140, 116, 1206, 558);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		panel_3.setVisible(true);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("hinh-nen-dong-dep-cho-powerpoint-32.gif"));
		lblNewLabel_2.setBounds(345, 96, 582, 317);
		panel_3.add(lblNewLabel_2);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(25, 25, 101, 22);
		contentPane.add(menuBar);

		JMenu File = new JMenu("File");
		File.setFont(new Font("Segoe UI", Font.BOLD, 16));
		menuBar.add(File);
		final JFileChooser fileDialog = new JFileChooser();
		JMenuItem OpenFile = new JMenuItem("Open File");
		OpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				fileDialog.setFileFilter(filter);
				fileDialog.setAcceptAllFileFilterUsed(false);
				int returnVal = fileDialog.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileDialog.getSelectedFile();
					dataInput = new DataInput(fileDialog.getSelectedFile().toString());

					bfs = new BFS(dataInput);
					// panel_3.setVisible(false);

					panel_2.setVisible(false);
					btnShortestPath.setVisible(false);
					demo.graph.clear();
					jTextBeginPoint.setText("");
					jTextEndPoint.setText("");
					jTextCurrentPath.setText("");
					jLaberCurrentPath.setVisible(false);
					jTextCurrentPath.setVisible(false);
					jScrollPane2.setVisible(false);
					panel_1.setVisible(false);

					// Khởi tạo lại
					cPath = "";

					listPath.clear();
					demo.showGraph(dataInput);
					countStepTwoNode.clear();
					// BEGIN Khởi tạo danh sách đường đi từ 1 đỉnh đến các đỉnh còn lại
					for (int i = 0; i <= dataInput.getMaxDinh(); i++) {
						List list = new ArrayList<String>();
						for (int j = 0; j < dataInput.getMaxDinh(); j++) {
							list.add("");
						}
						listPath.add(list);
					}

					for (int i = 0; i <= dataInput.getMaxDinh(); i++) {
						List list = new ArrayList<Integer>(0);
						for (int j = 0; j < dataInput.getMaxDinh(); j++) {
							list.add(0);
						}
						countStepTwoNode.add(list);
					}
					// END

					// BEGIN Những đỉnh có đỉnh kề số bước là 1

					panel.add(demo.getViewPanel());
					demo.Display();
					panel.setVisible(true);
					btnNewButton_5.setVisible(true);
					btnNewButton_9.setVisible(true);
					panel_3.setVisible(false);

				} else
					return;
			}
		});
		File.add(OpenFile);

		JMenuItem SaveImage = new JMenuItem("Save Image");
		SaveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportImage(panel);

			}
		});
		File.add(SaveImage);

		JMenu Tool = new JMenu("Tool");
		Tool.setFont(new Font("Segoe UI", Font.BOLD, 16));
		menuBar.add(Tool);

		JMenuItem ShortestPath = new JMenuItem("Shortest Path");
		ShortestPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextBeginPoint.setText("");
				jTextEndPoint.setText("");
				jTextCurrentPath.setText("");
				jTextBeginPoint.enable();
				jTextEndPoint.enable();
				b.setListData(new ArrayList<Integer>().toArray());
				numberCurrentStep.setText("");
				textField.setText("");
				CurrentNode.setText("");

				panel_2.setVisible(true);
				btnShortestPath.setVisible(true);
				jLaberCurrentPath.setVisible(true);
				jTextCurrentPath.setVisible(true);
				jScrollPane2.setVisible(true);
				panel_1.setVisible(false);
				demo.setColorDefault();

			}
		});
		Tool.add(ShortestPath);

		JMenuItem YourPath = new JMenuItem("Your Path");
		YourPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextBeginPoint.setText("");
				jTextEndPoint.setText("");
				jTextCurrentPath.setText("");
				jTextBeginPoint.enable();
				jTextEndPoint.enable();
				b.setListData(new ArrayList<Integer>().toArray());
				numberCurrentStep.setText("");
				textField.setText("");
				CurrentNode.setText("");
				panel_2.setVisible(true);
				panel_1.setVisible(true);
				jTextBeginPoint.enable();
				jTextEndPoint.enable();
				btnShortestPath.setVisible(false);
				jLaberCurrentPath.setVisible(true);
				jTextCurrentPath.setVisible(true);
				jScrollPane2.setVisible(true);
				demo.setColorDefault();

			}
		});
		Tool.add(YourPath);

		demo.getViewPanel().addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent mwe) {
				demo.zoomGraphMouseWheelMoved(mwe, demo.getViewPanel());
			}
		});
	}

	int dem = 0;

	private void exportImage(JPanel jPanel) {
		BufferedImage image = new BufferedImage(jPanel.getWidth(), jPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		jPanel.printAll(g);
		g.dispose();
		try {
			ImageIO.write(image, "jpg", new File("graph" + dem + ".jpg"));
			String currentPathString = System.getProperty("user.dir"); // lấy đường dẫn hiện tại của hệ thống
			File file = new File(currentPathString + "\\graph" + dem + ".jpg");
			dem++;
			Desktop desktop = Desktop.getDesktop();
			if (file.exists())
				desktop.open(file);
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}

	private void showYourPath() {
		panel_1.setVisible(true);
	}

	private boolean validation(JTextField jTextBegin, JTextField jTextEnd, int maxPoint) {
		if (jTextBegin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đỉnh bắt đầu");
			return false;
		} else if (jTextEnd.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đỉnh kết thúc");
			return false;
		}
		String begin = jTextBegin.getText();
		for (int i = 0; i < begin.length(); i++) {
			if (begin.charAt(i) < '0' || begin.charAt(i) > '9') {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập số (0->9), không nhập kí tự khác");
				return false;
			}
		}
		String end = jTextEnd.getText();
		for (int i = 0; i < end.length(); i++) {
			if (end.charAt(i) < '0' || end.charAt(i) > '9') {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập số (0->9), không nhập kí tự khác");
				return false;
			}
		}

		if (Integer.parseInt(end) > maxPoint || Integer.parseInt(begin) > maxPoint) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập điểm nhỏ hơn " + (maxPoint + 1));
			return false;
		}

		return true;
	}

	private boolean isContinute() {
		int n = JOptionPane.showConfirmDialog(panel, "Bạn có muốn tiếp tục chọn đường không ?", "Alert",
				JOptionPane.YES_NO_OPTION);
		return n == JOptionPane.YES_OPTION;

	}

	public List listNode(String s) {
		List arrayNode = new ArrayList<Integer>();
		if (s == "") {
			JOptionPane.showMessageDialog(null, "Không có đường đi");
		}
		String[] listNodefromToNode = s.split(" ");
		for (int i = 0; i < listNodefromToNode.length; i++) {
			arrayNode.add(Integer.parseInt(listNodefromToNode[i]));
		}
		return arrayNode;

	}
}