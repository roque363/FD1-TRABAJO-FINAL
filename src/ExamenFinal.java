public class ExamenFinal {

  static String[] countries = {"ARGPER", "BRABOL", "CHIECU", "COLURU", "CHIPER"};
  static String[] results = {"20", "50", "31", "03", "00"};

  public static void main(String[] args) {
    System.out.println("Partido con mayor cantidad de goles:");
    findHighestGoals();

    System.out.println("Partido con mayor diferencia de goles:");
    findHighestDifferenceMatch();

    System.out.println("Partidos en los que ha jugado Perú:");
    String[] peruMatches = findMatchesByCountry("PER");
    for (String match : peruMatches) {
      System.out.println(match);
    }
  }

  public static void findHighestGoals() {
    int maxGoals = 0;
    int indexMaxGoals = 0;

    for (int i = 0; i < results.length; i++) {
      int goals1 = Character.getNumericValue(results[i].charAt(0));
      int goals2 = Character.getNumericValue(results[i].charAt(1));
      int totalGoals = goals1 + goals2;

      if (totalGoals > maxGoals) {
        maxGoals = totalGoals;
        indexMaxGoals = i;
      }
    }

    System.out.println(countries[indexMaxGoals] + " con un total de " + maxGoals + " goles (" + results[indexMaxGoals] + ")");
  }

  public static void findHighestDifferenceMatch() {
    int maxDifference = 0;
    int indexMaxDifference = 0;

    for (int i = 0; i < results.length; i++) {
      int goals1 = Character.getNumericValue(results[i].charAt(0));
      int goals2 = Character.getNumericValue(results[i].charAt(1));
      int goalDifference = Math.abs(goals1 - goals2);

      if (goalDifference > maxDifference) {
        maxDifference = goalDifference;
        indexMaxDifference = i;
      }
    }
    System.out.println(countries[indexMaxDifference] + " con una diferencia de " + maxDifference + " goles  (" + results[indexMaxDifference] + ")");
  }

  public static String[] findMatchesByCountry(String country) {
    int count = 0;

    for (String match : countries) {
      if (match.contains(country)) {
        count++;
      }
    }

    String[] matches = new String[count];
    int index = 0;

    for (String match : countries) {
      if (match.contains(country)) {
        matches[index] = match;
        index++;
      }
    }

    return matches;
  }
}
