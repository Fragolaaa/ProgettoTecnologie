public class CardDeck //classe che gestisce il deck di carte dell'utente
{
    public List<Card> Cards { get; set; }

    public CardDeck() { ... }
    public void Shuffle() { ... }
    public List<Card> Draw(int count) { ... }
}

public CardDeck()
{
    Cards = new List<Card>();

    //Per ogni colore
    foreach (CardColor color in Enum.GetValues(typeof(CardColor)))
    {
        if (color != CardColor.Wild) //Le wild non hanno colore
        {
            foreach (CardValue val in Enum.GetValues(typeof(CardValue)))
            {
                switch (val)
                {
                    case CardValue.One:
                    case CardValue.Two:
                    case CardValue.Three:
                    case CardValue.Four:
                    case CardValue.Five:
                    case CardValue.Six:
                    case CardValue.Seven:
                    case CardValue.Eight:
                    case CardValue.Nine:
                        //due copie di carte valori da 1 a 9 per ogni colore
                        Cards.Add(new Card()
                        {
                            Color = color,
                            Value = val,
                            Score = (int)val
                        });
                        Cards.Add(new Card()
                        {
                            Color = color,
                            Value = val,
                            Score = (int)val
                        });
                        break;
                    case CardValue.Skip:
                    case CardValue.Reverse:
                    case CardValue.DrawTwo:
                        //due copie per colore delle Skip, cambio giro, e pesca due
                        Cards.Add(new Card()
                        {
                            Color = color,
                            Value = val,
                            Score = 20
                        });
                        Cards.Add(new Card()
                        {
                            Color = color,
                            Value = val,
                            Score = 20
                        });
                        break;

                    case CardValue.Zero:
                        //aggiungi una 0 di ogni colore
                        Cards.Add(new Card()
                        {
                            Color = color,
                            Value = val,
                            Score = 0
                        });
                        break;
                }
            }
        }
        else //carte "wild"
        {
            //aggiungi 4 carte "wild" regolari
            for (int i = 1; i <= 4; i++)
            {
                Cards.Add(new Card()
                {
                    Color = color,
                    Value = CardValue.Wild,
                    Score = 50
                });
            }
            //aggiungi 4 carte "pesca 4"
            for (int i = 1; i <= 4; i++)
            {
                Cards.Add(new Card()
                {
                    Color = color,
                    Value = CardValue.DrawFour,
                    Score = 50
                });
            }
        }
    }
}

public void Shuffle() //shuffle del deck
{
    Random r = new Random();

    List<Card> cards = Cards;

    for (int n = cards.Count - 1; n > 0; --n)
    {
        int k = r.Next(n + 1);
        Card temp = cards[n];
        cards[n] = cards[k];
        cards[k] = temp;
    }
}

public List<Card> Draw(int count)
{
    var drawnCards = Cards.Take(count).ToList();

    //Rimuovi la carta dal deck
    Cards.RemoveAll(x => drawnCards.Contains(x));

    return drawnCards;
}