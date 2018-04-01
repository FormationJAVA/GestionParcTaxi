package winFlotte;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JPanel;

import Flotte.Flotte;
import Flotte.Etat;
import Geometry.Point;


public class Pan extends JPanel implements MouseMotionListener {
		ArrayList<Flotte> voiture;
		Color bgC; //ArrierePlan
		Color cLibre;
		Color cOccupe;
		Color cMaintenance;
		ArrayList<Element> elemts;
		double xsize,ysize;
		
	Pan()
	{
		this.bgC = new Color (45,45,45);
		cLibre =new Color(129,194,52);
		cOccupe = new Color(210,20,10);
		cMaintenance = Color.cyan;
		this.setVisible(true);
		this.addMouseMotionListener(this);
		init ();
	}
	private void init ()
	{
		
		this.voiture = new ArrayList<Flotte>();
		this.elemts = new ArrayList<Element>();
		
		String mod="Clio";
		String im ="3214-118-13";
		Etat e = Etat.Libre;


		voiture.add(new Flotte (mod,im,e));
		
		mod="Ibiza";
		im ="114-116-13";
		e = Etat.Libre;

		
		voiture.add(new Flotte (mod,im,e));
		
		mod="Logan";
		im ="345-115-13";
		e = Etat.Libre;

		
		voiture.add(new Flotte (mod,im,e));
		mod="Megane";
		im ="25-116-13";
		e = Etat.Libre;

		
		voiture.add(new Flotte (mod,im,e));
		mod="308";
		im ="377-117-13";
		e = Etat.Libre;

		
		voiture.add(new Flotte (mod,im,e));
		mod="Clio";
		im ="278-112-13";
		e = Etat.Libre;

		
		voiture.add(new Flotte (mod,im,e));

		int i=0,j=0,n=2;
		xsize=120;
		ysize=120;
		if (this.getHeight()!=0)
			ysize=(this.getHeight()*n)/this.voiture.size();
		
		int xStr = 50,yStr = 100;
		Point PStart = new Point (xStr, yStr);
		Point Ptemp = new Point();
		int index=0;
		for (i=0;i<(this.voiture.size()/n)+1;i++)
		{
			
			
			for (j=0;j<n;j++)
			{
				
				index = (i*n)+j;
				if (index<this.voiture.size())
					this.elemts.add(new Element ( this.voiture.get(index),  new Point (PStart)) );
				else 
					break;
				PStart.x += (int) Math.round( xsize);
			}
			if (index>=this.voiture.size())
				break;
			PStart.x=xStr;
			PStart.y+=(int)Math.round(ysize);
		}
	}
	protected void paintComponent (Graphics g)
	{

		Graphics2D g2d= (Graphics2D) g; 
		g2d.setColor(this.bgC);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		//Activation de l'anti-aliasing pour le Text et pour les formes
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(new Font ("Times new Roman", Font.BOLD, 22));//Taille du Texte
		//Definir l'epesseur du trait
		g2d.setStroke(new BasicStroke(3));
		//Dessiner les rectagles definissant les zones
		drawZone(g2d, Etat.Libre);
		drawZone(g2d, Etat.Occupe);
		drawZone(g2d, Etat.enMaintenance);
		//Dessiner les elements
		for (int i=0; i<this.elemts.size();i++)
		{
			this.drawElement(g2d, this.elemts.get(i));
		}
		
	}
	public void drawZone (Graphics2D g2d, Etat e)
	{
		switch (e)
		{
		case Libre : 
			g2d.setColor(this.cLibre);
			g2d.drawRect(0, 0, (this.getWidth()/3)-2, this.getHeight());
			g2d.drawString(e.toString(), 50, 30);
			break;
		case Occupe : 
			g2d.setColor(this.cOccupe);
			g2d.drawRect((this.getWidth()/3)+2, 0, (this.getWidth()/3)-2, this.getHeight());
			g2d.drawString(e.toString(), (this.getWidth()/3)+50, 30);
			break;
		case enMaintenance : 
			g2d.setColor(this.cMaintenance);
			g2d.drawRect(((2*this.getWidth())/3)+2, 0, (this.getWidth()/3)-2, this.getHeight());
			g2d.drawString(e.toString(), ((2*this.getWidth())/3)+50, 30);
			break;
		
		
		}
	}
	private void drawElement (Graphics2D g2d, Element e)
	{
		int w=(int) xsize,h=(int) ysize;
		Color c = Color.WHITE;
		switch (e.voiture.etat)
		{
			case Occupe : c = this.cOccupe;break;
			case Libre : c = this.cLibre;break;
			case enMaintenance : c = this.cMaintenance;break;
		}
		g2d.setColor(c);

		g2d.drawRect(e.pos.x-(w/2), e.pos.y-(h/2), w, h);
		g2d.drawString(e.voiture.Model, e.pos.x-(w/4), e.pos.y-(h/4));
		
		g2d.fillRect(e.pos.x-(w/2), e.pos.y, w, h/2);
		g2d.setColor(Color.BLACK);
		g2d.drawString(e.voiture.immatriculation, e.pos.x-(w/2)+5, e.pos.y+(h/3));
		
		
	}

	@Override
	public void mouseDragged(MouseEvent ev) {
		// TODO Auto-generated method stub
		Point P = new Point(ev.getX(),ev.getY());
		for (int i=0;i<this.elemts.size();i++)
		{
			double dist = this.elemts.get(i).pos.getDistance(P);
			if (dist<50)
			{
				//Definition des limite de deplacement des elements
				if (P.y<(ysize/2) + 40 )
					P.y=(int) ((ysize/2) + 40);
				if (P.y>this.getHeight()-(ysize/2))
					P.y=(int) (this.getHeight()-(ysize/2));
				if (P.x<(xsize/2) )
					P.x=(int) (xsize/2);
				if (P.x>this.getWidth()-(xsize/2))
					P.x=(int) (this.getWidth()-(xsize/2));
					this.elemts.get(i).pos.setPoint(P);
					
					
					
				if (P.x< (this.getWidth())/3)
					//Si la position de l'element 
					//sur l'axe X est dans
					//le premier tiers (1/3) de la fenetre
					//Alors mettre la voiture dans l'etat libre
					this.elemts.get(i).voiture.etat=Etat.Libre;
				else 
				{//si l'element n'est pas dans le premier tiers
					if (P.x< (2*this.getWidth())/3)
						//Si la position de l'element 
						//sur l'axe X est dans
						//le deuxieme tiers (entre 1/3 et 2/3) de la fenetre
						//Alors mettre la voiture dans l'etat Occupee
						this.elemts.get(i).voiture.etat=Etat.Occupe;
					else
						//Si la position de l'element 
						//sur l'axe X est dans
						//le dernier tiers (entre 2/3 et 3/3) de la fenetre
						//Alors mettre la voiture dans l'etat enMaintenance
						this.elemts.get(i).voiture.etat=Etat.enMaintenance;
				}
				
				repaint();
				break;// Stop for
			}
		}
		
		
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
