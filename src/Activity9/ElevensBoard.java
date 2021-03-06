package Activity9;

import java.util.List;

/**
 * The ElevensBoard class represents the board in a game of Elevens.
 */
public class ElevensBoard extends Board {

	/**
	 * The size (number of cards) on the board.
	 */
	private static final int BOARD_SIZE = 9;

	/**
	 * The ranks of the cards for this game to be sent to the deck.
	 */
	private static final String[] RANKS =
		{"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

	/**
	 * The suits of the cards for this game to be sent to the deck.
	 */
	private static final String[] SUITS =
		{"spades", "hearts", "diamonds", "clubs"};

	/**
	 * The values of the cards for this game to be sent to the deck.
	 */
	private static final int[] POINT_VALUES =
		{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0, 0};

	/**
	 * Flag used to control debugging print statements.
	 */
	private static final boolean I_AM_DEBUGGING = false;


	/**
	 * Creates a new <code>ElevensBoard</code> instance.
	 */
	 public ElevensBoard() {
	 	super(BOARD_SIZE, RANKS, SUITS, POINT_VALUES);
	 }

	/**
	 * Determines if the selected cards form a valid group for removal.
	 * In Elevens, the legal groups are (1) a pair of non-face cards
	 * whose values add to 11, and (2) a group of three cards consisting of
	 * a jack, a queen, and a king in some order.
	 * @param selectedCards the list of the indices of the selected cards.
	 * @return true if the selected cards form a valid group for removal;
	 *         false otherwise.
	 */
	@Override
	public boolean isLegal(List<Integer> selectedCards) {
		int size = selectedCards.size();
		if (size == 2)
			// If size is 2, pair with sum of 11 is only possible combo. Then check that with the selected cards
			return containsPairSum11(selectedCards);
		else
			// Otherwise, only possible combo is 3 cards that contain a Jack, Queen, and King. So check that with the
			// selected cards
			return size == 3 && containsJQK(selectedCards);
	}

	/**
	 * Determine if there are any legal plays left on the board.
	 * In Elevens, there is a legal play if the board contains
	 * (1) a pair of non-face cards whose values add to 11, or (2) a group
	 * of three cards consisting of a jack, a queen, and a king in some order.
	 * @return true if there is a legal play left on the board;
	 *         false otherwise.
	 */
	@Override
	public boolean anotherPlayIsPossible() {
		return containsPairSum11(cardIndexes()) || containsJQK(cardIndexes());
	}

	/**
	 * Check for an 11-pair in the selected cards.
	 * @param selectedCards selects a subset of this board.  It is list
	 *                      of indexes into this board that are searched
	 *                      to find an 11-pair.
	 * @return true if the board entries in selectedCards
	 *              contain an 11-pair; false otherwise.
	 */
	private boolean containsPairSum11(List<Integer> selectedCards) {
		int size = selectedCards.size();

		if (size < 2)
			return false;

		// Iterate through each selected card and store a first card
		for (int first = 0; first < size; first++)
			// Iterate through each selected card again and store it as a first, as long as the index doesn't equal that
			// of the first card. Also, the index starts after the current index of the first card because all the cards
			// before have been checked against the first already, and checking again would waste resources
			for (int second = first + 1; second != first && second < size; second++)
				if (cardAt(selectedCards.get(first)).pointValue() +
						cardAt(selectedCards.get(second)).pointValue() == 11)
					return true;

		return false;
	}

	/**
	 * Check for a JQK in the selected cards.
	 * @param selectedCards selects a subset of this board.  It is list
	 *                      of indexes into this board that are searched
	 *                      to find a JQK group.
	 * @return true if the board entries in selectedCards
	 *              include a jack, a queen, and a king; false otherwise.
	 */
	private boolean containsJQK(List<Integer> selectedCards) {
		boolean hasJack = false,
				hasQueen = false,
				hasKing = false;

		if (selectedCards.size() < 3)
			return false;

		for (int selectedCard : selectedCards)
			switch (cardAt(selectedCard).rank()) {
				case "jack":
					hasJack = true;
					break;
				case "queen":
					hasQueen = true;
					break;
				case "king":
					hasKing = true;
					break;
			}

		return hasJack && hasQueen && hasKing;
	}
}
