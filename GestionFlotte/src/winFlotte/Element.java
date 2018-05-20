package winFlotte;
import Flotte.*;
import Geometry.*;
public class Element {

		public Flotte voiture;

		public Point pos;
		
		Element(Flotte v, Point p)
		{

			this.voiture = v;
			this.pos = p;
		}
		Element(Flotte v)
		{
			this.voiture = v;
			this.pos = new Point();
		}
		
}
