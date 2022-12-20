using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.GameObjects
{
    public class Card
    {
        public CardColor Color { get; set; }
        public CardValue Value { get; set; }
        public int Score { get; set; }
    //restituisco il lavore della carta
        public string DisplayValue 
        {
            get
            {
                if(Value == CardValue.Wild)
                {
                    return Value.ToString();
                }
                return Color.ToString() + " " + Value.ToString(); 
            }
        }
    }
}
