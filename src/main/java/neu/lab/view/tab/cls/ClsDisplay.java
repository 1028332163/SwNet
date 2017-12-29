package neu.lab.view.tab.cls;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import neu.lab.SysCons;
import neu.lab.core.SysInfo;
import neu.lab.view.Screen;
import neu.lab.view.ViewCons;
import neu.lab.view.ViewUtil;
import neu.lab.view.shop.ClsJarGShop;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.Action;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.expression.Predicate;
import prefuse.data.expression.parser.ExpressionParser;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.Renderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

public class ClsDisplay extends Display {
	private Graph clsG;
	Screen screen;

	public ClsDisplay(final Screen screen) {
		super(new Visualization());
		this.screen = screen;

		// this.setPreferredSize(new Dimension(Cons.WIDTH - Cons.PCK_SEL_W,
		// Cons.HEIGHT));
		this.setMinimumSize(new Dimension(ViewCons.FRAME_W - ViewCons.PCK_SEL_W, 0));
		// this.pan((Cons.WIDTH - Cons.PCK_SEL_W)/2, Cons.HEIGHT/2);
		this.pan(400, 350);

		initClsG();
		m_vis.add(ViewCons.CLS_G, this.clsG);

		m_vis.setInteractive(ViewCons.CLS_EGS, null, false);
		addListeners();
		addRender();
		m_vis.setValue(ViewCons.CLS_NDS, null, VisualItem.SHAPE, new Integer(Constants.SHAPE_ELLIPSE));

		m_vis.putAction("clsColor", getClsColorActs());
		m_vis.putAction("clsLayout", getClsLayoutActs());
		m_vis.putAction("placeJar", getPlaceJarAct());
		m_vis.run("clsColor");
		m_vis.run("clsLayout");
		m_vis.run("placeJar");

	}

	private void addRender() {
		Renderer nodeR = new ShapeRenderer(5);
		DefaultRendererFactory drf = new DefaultRendererFactory();
		drf.setDefaultRenderer(nodeR);
		m_vis.setRendererFactory(drf);
	}

	private void addListeners() {
		addControlListener(new DragControl()); // drag items around
		addControlListener(new PanControl()); // pan with background left-drag
		addControlListener(new ZoomControl()); // zoom with vertical right-drag
		addControlListener(new ZoomToFitControl());
		addControlListener(new FocusControl(1));
		addControlListener(new WheelZoomControl());
		addControlListener(new NeighborHighlightControl());
		m_vis.getGroup(Visualization.FOCUS_ITEMS).addTupleSetListener(new TupleSetListener() {
			public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem) {
				if (add.length == 1) {
					screen.updateMthdTab(add[0].getString("sig"));
					
				}
			}
		});
		addControlListener(new ControlAdapter() {
			public void itemEntered(VisualItem item, MouseEvent e) {
				if (item.canGetString("sig")) {
					screen.updateLabel(item.getString("sig"));
				}
			}

			public void itemExited(VisualItem item, MouseEvent e) {
				screen.updateLabel(null);
			}
		});
	}

	private void initClsG() {
		clsG = new ClsJarGShop().getGraph(SysInfo.c2jSys.nodes, SysInfo.c2jSys.edges);
	}

	private ActionList getClsLayoutActs() {
		ActionList layoutActs = new ActionList();
		ForceDirectedLayout force = new ForceDirectedLayout(ViewCons.CLS_G, false, true);
		layoutActs.add(force);
		return layoutActs;
	}

	private ActionList getClsColorActs() {
		ActionList colorActs = new ActionList(Activity.INFINITY);

		// int[] palette = DisplayUtil.getColors(SysInfo.getJarNum());
		DataColorAction colorNode = new DataColorAction(ViewCons.CLS_NDS, "jarSig", Constants.NOMINAL,
				VisualItem.FILLCOLOR, ColorLib.getCategoryPalette(SysInfo.getJarNum()));
		colorNode.add(VisualItem.FIXED, ColorLib.rgb(255, 100, 100));
		colorNode.add(VisualItem.HIGHLIGHT, ColorLib.rgb(145, 2, 100));

		ColorAction colorEdge = new ColorAction(ViewCons.CLS_EGS, VisualItem.STROKECOLOR, ColorLib.gray(200));

		colorActs.add(colorNode);
		colorActs.add(colorEdge);
		colorActs.add(new ColorAction(ViewCons.CLS_EGS, VisualItem.FILLCOLOR, ColorLib.gray(200)));
		colorActs.add(new RepaintAction());

		return colorActs;
	}

	private Action getPlaceJarAct() {
		return new RepaintAction() {
			@Override
			public void run(double frac) {
				Iterator<VisualItem> vIte = m_vis.visibleItems(ViewCons.CLS_NDS);
				while (vIte.hasNext()) {
					VisualItem vItem = vIte.next();
					Tuple t = vItem.getSourceTuple();
					String sig = (String) t.get("jarSig");
					// if (sig.startsWith("cmp")) {
					if (!SysCons.MY_JAR_NAME.equals(sig)) {
						vItem.setSize(3);
						vItem.setX(vItem.getX() + ViewUtil.getRdX());
						vItem.setY(vItem.getY() + 450 + ViewUtil.getRdY());
					}
				}
				m_vis.repaint();
			}
		};
	}

	/**
	 * ����jar����صĽڵ���ʾ(isShow==true)���߲���ʾ(isShow==false)
	 * 
	 * @param jar
	 * @param isShow
	 */
	public void showJar(String jar, boolean isShow) {
		// �ı���ؽڵ�
		Predicate ndP = (Predicate) ExpressionParser.parse("jarSig == '" + jar + "'");
		m_vis.setVisible(ViewCons.CLS_NDS, ndP, isShow);
		// �ı���ر�
		Predicate egP = (Predicate) ExpressionParser.parse("srcJar =='" + jar + "'||tgtJar == '" + jar + "'");
		m_vis.setVisible(ViewCons.CLS_EGS, egP, isShow);
	}
}