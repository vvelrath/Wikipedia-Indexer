/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

/**
 * @author vvelrath THis class is responsible for assigning a partition to a
 *         given term. The static methods imply that all instances of this class
 *         should behave exactly the same. Given a term, irrespective of what
 *         instance is called, the same partition number should be assigned to
 *         it.
 */
public class Partitioner {

	static int partitionnumber;

	/**
	 * Method to get the total number of partitions THis is a pure design choice
	 * on how many partitions you need and also how they are assigned.
	 * 
	 * @return: Total number of partitions
	 */
	public static int getNumPartitions() {
		// TODO: Implement this method
		return 8;
	}

	/**
	 * Method to fetch the partition number for the given term. The partition
	 * numbers should be assigned from 0 to N-1 where N is the total number of
	 * partitions.
	 * 
	 * @param term
	 *            : The term to be looked up
	 * @return The assigned partition number for the given term
	 */
	public static int getPartitionNumber(String term) {
		//System.out.println(term);
		if (term.length() > 0) {
			if (((term.charAt(0) >= 65) && (term.charAt(0) <= 77))
					|| ((term.charAt(0) >= 96) && (term.charAt(0) <= 108))) {
				partitionnumber = 1;

			} else if (((term.charAt(0) >= 78) && (term.charAt(0) <= 90))
					|| ((term.charAt(0) >= 109) && (term.charAt(0) <= 122))) {
				partitionnumber = 2;
			}

			else if ((term.charAt(0) == '0') || (term.charAt(0) == '1')
					|| (term.charAt(0) == '2') || (term.charAt(0) == '3')
					|| (term.charAt(0) == '4') || (term.charAt(0) == '5')
					|| (term.charAt(0) == '6') || (term.charAt(0) == '7')
					|| (term.charAt(0) == '8') || (term.charAt(0) == '9')) {
				partitionnumber = 3;
			} else {
				partitionnumber = 4;
			}
		} else
			partitionnumber = 4;

		return partitionnumber;
	}

}
