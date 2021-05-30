package Final;

import java.awt.Event;
import java.awt.event.MouseWheelEvent;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.ThreadingModel;
import org.graphstream.ui.view.ViewerPipe;

public class Graphic {

	Graph graph = new SingleGraph("Đồ thị nhóm 6", false, true);

	Viewer viewer = new SwingViewer(graph, ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
	ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);

	Layout layout = new SpringBox(false);

	public Graphic() {

	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public void showGraph(DataInput dataInput) {
		graph.clear();

		graph.addSink(layout);

		layout.addAttributeSink(graph);

		System.setProperty("org.graphstream.ui", "swing");
		graph.setAttribute("ui.stylesheet", styleSheet);
		graph.setAutoCreate(true);
		graph.setStrict(false);

		for (int i = 0; i < dataInput.getDanhSachDinh().size(); i++) {
			for (Integer arrays : dataInput.getDanhSachDinh().get(i)) {
				String s = i + "_" + arrays;
				graph.addEdge(s, "" + i, "" + arrays, true);
			}
		}

		for (Node node : graph) {
			node.setAttribute("label", node.getId());
		}

	}

	protected String styleSheet = "node {" + "	shape: circle;" + "	size: 20px;" + "	fill-mode: plain;"
			+ "	fill-color: white;" + "	stroke-mode: plain;" + "	stroke-color: grey;" + "	stroke-width: 1px;" +

			"}" + "node.marked {" + "	fill-color: red;" + "}" + "node.default {" + "	fill-color: white;" + "}"
			+ "edge.marked {" + "	fill-color: green;" + "}" + "edge.default {" + "	fill-color: black;" + "}"
			+ "edge.yellow {" + "	fill-color: yellow;" + "}" + "edge.default {" + "	fill-color: black;" + "}";

	public void Display() {
		graph.setAttribute("ui.quality");
		graph.setAttribute("ui.antialias");
		System.setProperty("org.graphstream.ui", "swing");
		// viewer.enableAutoLayout();
		while (layout.getStabilization() < 0.9) {
			layout.compute();
		}
		;

	};

	public void setColorNode(String id) {
		if (graph.getNode(id) != null) {
			graph.getNode(id).setAttribute("ui.class", "marked");
		}

	}

	public void setColorEdgeDefault(int a, String b) {

		graph.getEdge(a + "_" + b).setAttribute("ui.class", "default");

	}

	public void setColorNodeDefault(String a) {

		graph.getNode(a).setAttribute("ui.class", "default");

	}

	public void setColorEdge(String id) {
		if (graph.getEdge(id) != null) {
			graph.getEdge(id).setAttribute("ui.class", "marked");
		}

	}

	public void setColorDefault() {
		for (Node node : graph) {
			node.setAttribute("ui.class", "default");
		}
		graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
		;

	}

	@SuppressWarnings("deprecation")
	void zoomGraphMouseWheelMoved(MouseWheelEvent mwe, ViewPanel view_panel) {
		if (Event.ALT_MASK != 0) {
			if (mwe.getWheelRotation() > 0) {
				double new_view_percent = view_panel.getCamera().getViewPercent() + 0.05;
				view_panel.getCamera().setViewPercent(new_view_percent);
			} else if (mwe.getWheelRotation() < 0) {
				double current_view_percent = view_panel.getCamera().getViewPercent();
				if (current_view_percent > 0.05) {
					view_panel.getCamera().setViewPercent(current_view_percent - 0.05);
				}
			}
		}
	}

	public ViewPanel getViewPanel() {
		return viewPanel;
	}

	public void setViewPanel(ViewPanel viewPanel) {
		this.viewPanel = viewPanel;
	}

}
