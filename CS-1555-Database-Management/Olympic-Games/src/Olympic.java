/**
 * This class is the main class for Olympic-Games. The main method takes valid user inputs
 * and prints out relative method data. Requires existence of user account in database, method
 * access is dependant on user role.
 *
 * @author Solomon Heisey
 * @version 1.0
 * @since 2020-04-04
 *
 */


import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import static java.sql.Date.*;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

class Olympic {
    Connection dbcon;
    private int currUserID;
    private int currUserRoleID;

    public static void main(String[] args) throws SQLException {

        boolean breakStatement = false;
        do {
            Scanner in = new Scanner(System.in);
            Olympic olympic = new Olympic();
            System.out.println("Welcome to the Olympic Games Simulation! Please login using your credentials below");
            boolean loggedIn = false;

            //login prompt
            while (!loggedIn) {
                System.out.println();
                System.out.print("Username: ");
                String username = in.nextLine();
                System.out.print("Password: ");
                String password = in.nextLine();
                loggedIn = olympic.login(username, password);
            }

            for (int i = 0; i < 50; ++i) System.out.println();

            //if current user is organizer
            if (olympic.currUserRoleID == 1) {
                int choice = 0;
                while (choice != 10) {
                    System.out.println();
                    System.out.println();
                    System.out.println("1: Create User");
                    System.out.println("2: Drop User");
                    System.out.println("3: Create Event");
                    System.out.println("4: Add Event Outcome");
                    System.out.println("5: Display Sport");
                    System.out.println("6: Display Event");
                    System.out.println("7: Country Ranking");
                    System.out.println("8: Top K Athletes");
                    System.out.println("9: Connected Athletes");
                    System.out.println("10: Logout");
                    System.out.println("11: Exit");

                    System.out.print("Choice: ");
                    choice = in.nextInt();
                    boolean failure;

                    switch (choice) {
                        case 1: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Username: ");
                                String username = in.nextLine();
                                System.out.print("Passkey: ");
                                String passkey = in.nextLine();
                                System.out.print("Role ID: ");
                                int roleID = in.nextInt();
                                in.nextLine();
                                failure = olympic.createUser(username, passkey, roleID);
                            }
                        } break;
                        case 2: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Username: ");
                                String username = in.nextLine();
                                failure = olympic.dropUser(username);
                            }
                        } break;
                        case 3: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Sport ID: ");
                                int sportID = in.nextInt();
                                System.out.print("Venue ID: ");
                                int venueID = in.nextInt();
                                System.out.print("Date (yyyy-MM-dd): ");
                                String date = in.nextLine();
                                System.out.print("Gender (m/f): ");
                                char gender = in.nextLine().charAt(0);
                                failure = olympic.createEvent(sportID, venueID, date, gender);
                            }
                        } break;
                        case 4: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic City: ");
                                String olympicCity = in.nextLine();
                                System.out.print("Olympic Year: ");
                                int olympicYear = in.nextInt();
                                System.out.print("Event ID: ");
                                int eventID = in.nextInt();
                                System.out.print("Participant ID: ");
                                int participantID = in.nextInt();
                                System.out.print("Team ID: ");
                                int teamID = in.nextInt();
                                System.out.print("Position: ");
                                int position = in.nextInt();
                                failure = olympic.addEventOutcome(olympicCity, olympicYear, eventID, participantID, teamID, position);
                            }
                        } break;
                        case 5: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Sport name: ");
                                String sportName = in.nextLine();
                                failure = olympic.displaySport(sportName);
                            }
                        } break;
                        case 6: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic City: ");
                                String olympicCity = in.nextLine();
                                System.out.print("Olympic Year: ");
                                int olympicYear = in.nextInt();
                                System.out.print("Event ID: ");
                                int eventID = in.nextInt();
                                failure = olympic.displayEvent(olympicCity, olympicYear, eventID);
                            }
                        } break;
                        case 7: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic ID: ");
                                int olympicID = in.nextInt();
                                failure = olympic.countryRanking(olympicID);
                            }
                        } break;
                        case 8: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic ID: ");
                                int olympicID = in.nextInt();
                                System.out.print("k: ");
                                int k = in.nextInt();
                                failure = olympic.topkAthletes(olympicID, k);
                            }
                        } break;
                        case 9: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Participant ID: ");
                                int participantID = in.nextInt();
                                System.out.print("Olympic ID: ");
                                int olympicID = in.nextInt();
                                System.out.print("n: ");
                                int n = in.nextInt();
                                failure = olympic.connectedAthletes(participantID, olympicID, n);
                            }
                        } break;
                        case 10: {
                            for (int i = 0; i < 50; ++i) System.out.println();
                            olympic.logout();
                            breakStatement = true;

                        } break;
                        case 11: {
                            olympic.exit();
                        } break;
                        default: {
                            System.out.println();
                            System.out.println("Invalid choice, please try again");
                        }
                    }
                }
            }

            //if current user is a coach
            else if (olympic.currUserRoleID == 2) {
                int choice = 0;
                while (choice != 11) {
                    System.out.println();
                    System.out.println();
                    System.out.println("1: Create Team");
                    System.out.println("2: Register Team");
                    System.out.println("3: Add Participant");
                    System.out.println("4: Add Team Member");
                    System.out.println("5: Drop Team Member");
                    System.out.println("6: Display Sport");
                    System.out.println("7: Display Event");
                    System.out.println("8: Country Ranking");
                    System.out.println("9: Top K Athletes");
                    System.out.println("10: Connected Athletes");
                    System.out.println("11: Logout");
                    System.out.println("12: Exit");

                    System.out.print("Choice: ");
                    choice = in.nextInt();
                    in.nextLine();
                    boolean failure;

                    switch (choice) {
                        case 1: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic City: ");
                                String olympicCity = in.nextLine();
                                System.out.print("Olympic Year: ");
                                int olympicYear = in.nextInt();
                                in.nextLine();
                                System.out.print("Sport Name: ");
                                String sportName = in.nextLine();
                                System.out.print("Country Name: ");
                                String country = in.nextLine();
                                System.out.print("Team Name: ");
                                String teamName = in.nextLine();
                                System.out.print("Coach ID: ");
                                int coachID = in.nextInt();
                                in.nextLine();
                                failure = olympic.createTeam(olympicCity, olympicYear,sportName, country,teamName,coachID);
                            }
                        }break;
                        case 2: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Team ID: ");
                                int teamID = in.nextInt();
                                in.nextLine();
                                System.out.print("Event ID: ");
                                int eventID = in.nextInt();
                                in.nextLine();
                                failure = olympic.registerTeam(teamID, eventID);
                            }
                        } break;
                        case 3: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("First name: ");
                                String fname = in.nextLine();
                                System.out.print("Last name: ");
                                String lname = in.nextLine();
                                System.out.print("Nationality: ");
                                String nationality = in.nextLine();
                                System.out.print("Birthplace: ");
                                String birthplace = in.nextLine();
                                System.out.print("dob (yyyy-MM-dd): ");
                                String dob = in.nextLine();
                                failure = olympic.addParticipant(fname, lname, nationality, birthplace, dob);
                            }
                        } break;
                        case 4: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Team ID: ");
                                int teamID = in.nextInt();
                                in.nextLine();
                                System.out.print("Participant ID: ");
                                int participantID = in.nextInt();
                                in.nextLine();
                                failure = olympic.addTeamMember(teamID,participantID);
                            }
                        } break;
                        case 5: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Participant ID: ");
                                int participantID = in.nextInt();
                                in.nextLine();
                                failure = olympic.dropTeamMember(participantID);
                            }
                        } break;
                        case 6: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Sport name: ");
                                String sportName = in.nextLine();
                                failure = olympic.displaySport(sportName);
                            }
                        } break;
                        case 7: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic City: ");
                                String olympicCity = in.nextLine();
                                System.out.print("Olympic Year: ");
                                int olympicYear = in.nextInt();
                                in.nextLine();
                                System.out.print("Event ID: ");
                                int eventID = in.nextInt();
                                in.nextLine();
                                failure = olympic.displayEvent(olympicCity, olympicYear, eventID);
                            }
                        } break;
                        case 8: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic ID: ");
                                int olympicID = in.nextInt();
                                in.nextLine();
                                failure = olympic.countryRanking(olympicID);
                            }
                        } break;
                        case 9: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic ID: ");
                                int olympicID = in.nextInt();
                                in.nextLine();
                                System.out.print("k: ");
                                int k = in.nextInt();
                                in.nextLine();
                                failure = olympic.topkAthletes(olympicID, k);
                            }
                        } break;
                        case 10: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Participant ID: ");
                                int participantID = in.nextInt();
                                in.nextLine();
                                System.out.print("Olympic ID: ");
                                int olympicID = in.nextInt();
                                in.nextLine();
                                System.out.print("n: ");
                                int n = in.nextInt();
                                in.nextLine();
                                failure = olympic.connectedAthletes(participantID, olympicID, n);
                            }
                        } break;
                        case 11: {
                            for (int i = 0; i < 50; ++i) System.out.println();
                            olympic.logout();
                            breakStatement = true;
                        } break;
                        case 12: {
                            olympic.exit();
                        }
                        default: {
                            System.out.println();
                            System.out.println("Invalid choice, please try again");
                        }
                    }
                }

            // if current user is a guest
            } else if (olympic.currUserRoleID == 3) {

                int choice = 0;
                while (choice != 6) {
                    System.out.println();
                    System.out.println();
                    System.out.println("1: Display Sport");
                    System.out.println("2: Display Event");
                    System.out.println("3: Country Ranking");
                    System.out.println("4: Top K Athletes");
                    System.out.println("5: Connected Athletes");
                    System.out.println("6: Logout");
                    System.out.println("7: Exit");

                    System.out.print("Choice: ");
                    choice = in.nextInt();
                    in.nextLine();
                    boolean failure;

                    switch (choice) {
                        case 1: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Sport name: ");
                                String sportName = in.nextLine();
                                failure = olympic.displaySport(sportName);
                            }
                        } break;
                        case 2: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic City: ");
                                String olympicCity = in.nextLine();
                                System.out.print("Olympic Year: ");
                                int olympicYear = in.nextInt();
                                in.nextLine();
                                System.out.print("Event ID: ");
                                int eventID = in.nextInt();
                                in.nextLine();
                                failure = olympic.displayEvent(olympicCity, olympicYear, eventID);
                            }
                        } break;
                        case 3: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic ID: ");
                                int olympicID = in.nextInt();
                                in.nextLine();
                                failure = olympic.countryRanking(olympicID);
                            }
                        } break;
                        case 4: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Olympic ID: ");
                                int olympicID = in.nextInt();
                                in.nextLine();
                                System.out.print("k: ");
                                int k = in.nextInt();
                                in.nextLine();
                                failure = olympic.topkAthletes(olympicID, k);
                            }
                        } break;
                        case 5: {
                            failure = false;
                            while (!failure) {
                                System.out.println();
                                System.out.print("Participant ID: ");
                                int participantID = in.nextInt();
                                in.nextLine();
                                System.out.print("Olympic ID: ");
                                int olympicID = in.nextInt();
                                in.nextLine();
                                System.out.print("n: ");
                                int n = in.nextInt();
                                in.nextLine();
                                failure = olympic.connectedAthletes(participantID, olympicID, n);
                            }
                        } break;
                        case 6: {
                            for (int i = 0; i < 50; ++i) System.out.println();
                            olympic.logout();
                            breakStatement = true;
                        } break;
                        case 7: {
                            olympic.exit();
                        }
                        default: {
                            System.out.println();
                            System.out.println("Invalid choice, please try again");
                        }
                    }
                }
            } else {
                System.out.println();
            }
        } while(breakStatement);
    }

    Olympic() throws SQLException  {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
        String username = "soh22";
        String password = "4180629";
        dbcon = DriverManager.getConnection(url, username, password);
    }

    boolean dropUser(String username) throws SQLException {

        PreparedStatement validateLogin = dbcon.prepareStatement("SELECT * FROM USER_ACCOUNT WHERE username =?");
        validateLogin.setString(1, username);
        ResultSet resultSet = validateLogin.executeQuery();

        if(!resultSet.next()) {
            System.out.println("Invalid username, please try again");
            return false;
        }

        int tempUserID = resultSet.getInt("user_id");

        PreparedStatement deleteUsername = dbcon.prepareStatement("DELETE FROM USER_ACCOUNT WHERE username =?");
        deleteUsername.setString(1, username);
        ResultSet rs = deleteUsername.executeQuery();
        System.out.println("User has been deleted from the system");

        if (tempUserID == currUserID)
            exit();
        return true;
    }

    boolean countryRanking(int olympicID) throws SQLException {

        //validates existence of olympic_id
        PreparedStatement checkOlympic = dbcon.prepareStatement("SELECT * FROM OLYMPICS WHERE olympic_id=?");
        checkOlympic.setInt(1, olympicID);
        ResultSet validOlympic = checkOlympic.executeQuery();
        if(!validOlympic.next()) {
            System.out.println("Invalid Olympic");
            return false;
        }

        //stored procedure to display country ranking from given olympic id
        CallableStatement displayRanks = dbcon.prepareCall("BEGIN PROC_GET_OLYMPIC(?,?); END;");
        displayRanks.setInt(1, olympicID);
        displayRanks.registerOutParameter(2, OracleTypes.CURSOR);
        displayRanks.executeQuery();
        ResultSet stats = ((OracleCallableStatement) displayRanks).getCursor(2);

        if(!stats.next()) {
            System.out.println("No countries found for this olympics");
            return false;
        }

        System.out.printf( "%-6s%-14s%-15s%-6s%-8s%-8s%-14s", "Rank", "Country Code", "First Olympic", "Gold", "Silver", "Bronze", "Total Points");
        System.out.println();

        int rank;
        String countryCode;
        int dob;
        int gold;
        int silver;
        int bronze;
        int totalPoints;

        //formats print statements
        do {
            rank = stats.getInt("rank");
            countryCode = stats.getString("country_code");
            dob = stats.getInt("dob");
            gold = stats.getInt("gold");
            silver = stats.getInt("silver");
            bronze = stats.getInt("bronze");
            totalPoints = stats.getInt("total_points");
            System.out.printf( "%-6s%-14s%-15s%-6s%-8s%-8s%-14s", rank, countryCode, dob, gold, silver, bronze, totalPoints);
            System.out.println();
        } while(stats.next());
        return true;
    }

    boolean dropTeamMember(int participantID) throws SQLException {

        //validates given participant id
        PreparedStatement getParticipant = dbcon.prepareStatement("SELECT * FROM PARTICIPANT WHERE participant_id =?");
        getParticipant.setInt(1, participantID);
        ResultSet participantResult = getParticipant.executeQuery();
        if(!participantResult.next()) {
            System.out.println("Invalid participant ID, please try again");
            return false;
        }

        PreparedStatement deleteParticipant = dbcon.prepareStatement("DELETE FROM PARTICIPANT WHERE participant_ID =?");
        deleteParticipant.setInt(1, participantID);
        ResultSet rs =  deleteParticipant.executeQuery();

        System.out.println("Participant has been deleted");
        return true;
    }

    boolean topkAthletes(int olympicID, int k) throws SQLException {

        if(k < 0) {
            System.out.println("K cannot be negative");
            return false;
        }

        //validates existence of olympic_id
        PreparedStatement checkOlympic = dbcon.prepareStatement("SELECT * FROM OLYMPICS WHERE olympic_id=?");
        checkOlympic.setInt(1, olympicID);
        ResultSet validOlympic = checkOlympic.executeQuery();
        if(!validOlympic.next()) {
            System.out.println("Invalid Olympic");
            return false;
        }

        //stored procedure to display k ranks of given olympics
        CallableStatement displayRanks = dbcon.prepareCall("BEGIN PROC_GET_ATHLETES(?,?,?); END;");
        displayRanks.setInt(1,olympicID);
        displayRanks.setInt(2,k);
        displayRanks.registerOutParameter(3, OracleTypes.CURSOR);
        displayRanks.executeQuery();
        ResultSet stats = ((OracleCallableStatement) displayRanks).getCursor(3);

        if(!stats.next()) {
            System.out.println("No participants found for this olympics");
            return false;
        }

        System.out.printf( "%-8s%-30s%-16s%-6s%-8s%-8s%-14s", "Rank", "Full Name", "Participant ID", "Gold", "Silver", "Bronze", "Total Points");
        System.out.println();

        int rank;
        String fullName;
        int participantID;
        int gold;
        int silver;
        int bronze;
        int totalPoints;

        //formats print statements
        do {
            rank = stats.getInt("rank");
            fullName = stats.getString("full_name");
            participantID = stats.getInt("participant_id");
            gold = stats.getInt("gold");
            silver = stats.getInt("silver");
            bronze = stats.getInt("bronze");
            totalPoints = stats.getInt("total_points");
            System.out.printf( "%-8s%-30s%-16s%-6s%-8s%-8s%-14s", rank, fullName, participantID, gold, silver, bronze, totalPoints);
            System.out.println();
        } while(stats.next());
        return true;
    }

    boolean createUser(String username, String passkey, int role_ID) throws SQLException {

        //username must be present
        if (username == null) {
            System.out.println("Please enter a username");
            return false;
        }

        //passkey must be present
        if (passkey == null) {
            System.out.println("Please enter a password");
            return false;
        }

        //validates uniqueness of username
        PreparedStatement checkDups = dbcon.prepareStatement("SELECT * FROM USER_ACCOUNT WHERE username=?");
        checkDups.setString(1, username);
        ResultSet dups = checkDups.executeQuery();
        if(dups.next()) {
            System.out.println("Please pick another username");
            return false;
        }

        //validates existence of role id
        PreparedStatement checkValidRole = dbcon.prepareStatement("SELECT * FROM USER_ROLE WHERE ROLE_ID=?");
        checkValidRole.setInt(1, role_ID);
        ResultSet validRole = checkValidRole.executeQuery();
        if(!validRole.next()) {
            System.out.println("Invalid role");
            return false;
        }

        PreparedStatement createUser = dbcon.prepareStatement("INSERT INTO USER_ACCOUNT(username, passkey, role_id, last_login) VALUES(?,?,?,?)");

        java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        createUser.setString(1,username);
        createUser.setString(2, passkey);
        createUser.setInt(3, role_ID);
        createUser.setDate(4, sqlDate);
        createUser.executeQuery();

        System.out.println("Success! User has been created");
        return true;
    }

    boolean createEvent(int sportID, int venueID, String date, char gender) throws SQLException {

        //date must be present
        if (date == null) {
            System.out.println("Please enter an event time");
            return false;
        }

        //gender must be present
        if (gender != 'm' && gender != 'f') {
            System.out.println("Please enter a valid gender");
            return false;
        }

        //sets integer which is used in db, 1 for men, 0 for women
        int genderBinary = (gender == 'm') ? 1 : 0;

        //validates sport ID
        PreparedStatement validSport = dbcon.prepareStatement("SELECT * FROM SPORT WHERE sport_ID=?");
        validSport.setInt(1, sportID);
        ResultSet sportResult = validSport.executeQuery();
        if(!sportResult.next()) {
            System.out.println("Invalid sport ID, please try again");
            return false;
        }

        //validates venue ID
        PreparedStatement validVenue = dbcon.prepareStatement("SELECT * FROM VENUE WHERE venue_ID=?");
        validVenue.setInt(1, venueID);
        ResultSet venueResult = validVenue.executeQuery();
        if(!venueResult.next()) {
            System.out.println("Invalid venue ID, please try again");
            return false;
        }

        //converts date string to sql Date
        java.sql.Date sqlDate;
        sqlDate = valueOf(date);

        //inserts new event into the event table
        PreparedStatement createEvent = dbcon.prepareStatement("INSERT INTO EVENT(SPORT_ID, VENUE_ID, GENDER, EVENT_TIME) VALUES(?,?,?,?)");
        createEvent.setInt(1, sportID);
        createEvent.setInt(2, venueID);
        createEvent.setInt(3, genderBinary);
        createEvent.setDate(4, sqlDate);

        //tries insert, if venue is full insert does not go through
        try {
            createEvent.executeQuery();
        } catch(SQLException e) {
            if(e.getErrorCode() == 20000) {
                System.out.println("Current venue is full at this time");
                return false;
            }
        }

        System.out.println("Event has been added successfully");
        return true;
    }

    boolean addEventOutcome(String olympicCity, int olympicYear, int eventID, int participantID, int teamID, int position) throws SQLException {
        int olympicID;

        if(olympicCity == null) {
            System.out.println("Please enter an olympic city");
            return false;
        }

        //validates existence of given olympic city and olympic year
        PreparedStatement validateOlympic = dbcon.prepareStatement("SELECT * FROM OLYMPICS WHERE host_city =? AND EXTRACT(YEAR FROM opening_date) =?");
        validateOlympic.setString(1, olympicCity);
        validateOlympic.setInt(2, olympicYear);
        ResultSet olympicResult = validateOlympic.executeQuery();
        if(olympicResult.next())
            olympicID = olympicResult.getInt("olympic_ID");
        else {
            System.out.println(olympicCity + " " + olympicYear + " was not found in the database, please try again");
            return false;
        }

        //validates existence of team id
        PreparedStatement checkValidTeam = dbcon.prepareStatement("SELECT * FROM TEAM WHERE team_id=?");
        checkValidTeam.setInt(1, teamID);
        ResultSet validTeam = checkValidTeam.executeQuery();
        if(!validTeam.next()) {
            System.out.println("Invalid team");
            return false;
        }

        //validates given participant id
        PreparedStatement getParticipant = dbcon.prepareStatement("SELECT * FROM PARTICIPANT WHERE participant_id =?");
        getParticipant.setInt(1, participantID);
        ResultSet participantResult = getParticipant.executeQuery();
        if(!participantResult.next()) {
            System.out.println("Invalid participant ID, please try again");
            return false;
        }

        //validates existence of given eventID
        PreparedStatement validateEvent = dbcon.prepareStatement("SELECT * FROM EVENT WHERE event_id =?");
        validateEvent.setInt(1, eventID);
        ResultSet eventResult = validateEvent.executeQuery();
        if(!eventResult.next()) {
            System.out.println("Event " + eventID + " was not found in the database, please try again");
            return false;
        }

        PreparedStatement addEventOutcome = dbcon.prepareStatement("INSERT INTO SCOREBOARD(olympics_id, event_id, team_id, participant_id, position) VALUES(?,?,?,?,?)");
        addEventOutcome.setInt(1,olympicID);
        addEventOutcome.setInt(2,eventID);
        addEventOutcome.setInt(3, teamID);
        addEventOutcome.setInt(4,participantID);
        addEventOutcome.setInt(5,position);
        addEventOutcome.executeQuery();

        System.out.println("Event outcome has been added to the scoreboard");
        return true;
    }

     boolean login(String username, String passkey) throws SQLException {
        PreparedStatement validateLogin = dbcon.prepareStatement("SELECT * FROM USER_ACCOUNT WHERE username =? AND passkey =?");
        validateLogin.setString(1, username);
        validateLogin.setString(2, passkey);

        ResultSet resultSet = validateLogin.executeQuery();

        if(resultSet.next()) {
            currUserID = resultSet.getInt("user_id");
            currUserRoleID = resultSet.getInt("role_id");
            System.out.println("Welcome, " + username + "!");
            return true;
        }
        System.out.println("Invalid credentials, please try again");
        return false;
     }

     //given sport name this finds all events associated with sport
     boolean displaySport(String sportName) throws SQLException {
         int sportID;
         int year;

         //validates sport name and gets associated id
         PreparedStatement getSport = dbcon.prepareStatement("SELECT * FROM SPORT WHERE sport_name=?");
         getSport.setString(1, sportName);
         ResultSet sportResult = getSport.executeQuery();

         //gets dob year
         if(sportResult.next()) {
             sportID = sportResult.getInt("sport_ID");
             java.sql.Date dob = sportResult.getDate("dob");
             java.util.Date tempDob = new java.util.Date(dob.getTime());
             Calendar calendar = new GregorianCalendar();
             calendar.setTime(tempDob);
             year = calendar.get(Calendar.YEAR);
         }
         else {
             System.out.println(sportName + " was not found in the database, please try again");
             return false;
         }

         System.out.println(sportName + " debuted in the " + year + " olympics");

         //stored procedure to sort events based on medals and olympic year
         CallableStatement displaySport = dbcon.prepareCall("BEGIN PROC_GET_EVENTS(?,?); END;");
         displaySport.setInt(1,sportID);
         displaySport.registerOutParameter(2, OracleTypes.CURSOR);
         displaySport.executeQuery();
         ResultSet stats = ((OracleCallableStatement) displaySport).getCursor(2);

         if(!stats.next()){
             System.out.println("No events found for this sport");
             return false;
         }

         System.out.printf( "%-12s%-10s%-30s%-20s", "Event ID", "Gender", "Full Name", "Country");
         System.out.println();

         int eventID;
         char gender;
         String name;
         String country;

         //formats print statements
         do {
             eventID = stats.getInt("event_id");
             gender = (stats.getInt("gender") == 1) ? 'M': 'F';
             name = stats.getString("full_name");
             country = stats.getString("country");
             System.out.printf( "%-12s%-10s%-30s%-20s", eventID, gender, name, country);
             System.out.println();
         } while(stats.next());
         return true;
     }

     boolean displayEvent(String olympicCity, int olympicYear, int eventID) throws SQLException {

        int olympicID;

        if(olympicCity == null) {
            System.out.println("Please enter an olympic city");
            return false;
        }

        //validates existence of given olympic city and olympic year
        PreparedStatement validateOlympic = dbcon.prepareStatement("SELECT * FROM OLYMPICS WHERE host_city =? AND EXTRACT(YEAR FROM opening_date) =?");
        validateOlympic.setString(1, olympicCity);
        validateOlympic.setInt(2, olympicYear);
        ResultSet olympicResult = validateOlympic.executeQuery();
        if(olympicResult.next())
            olympicID = olympicResult.getInt("olympic_ID");
        else {
            System.out.println(olympicCity + " " + olympicYear + " was not found in the database, please try again");
            return false;
        }

         //validates existence of given eventID
         PreparedStatement validateEvent = dbcon.prepareStatement("SELECT * FROM EVENT WHERE event_id =?");
         validateEvent.setInt(1, eventID);
         ResultSet eventResult = validateEvent.executeQuery();
         if(!eventResult.next()) {
             System.out.println("Event " + eventID + " was not found in the database, please try again");
             return false;
         }

         //stored procedure to get participants and stats from given olympics
         CallableStatement displayParticipants = dbcon.prepareCall("BEGIN PROC_GET_PARTICIPANTS(?,?,?); END;");
         displayParticipants.setInt(1,olympicID);
         displayParticipants.setInt(2,eventID);
         displayParticipants.registerOutParameter(3, OracleTypes.CURSOR);
         displayParticipants.executeQuery();
         ResultSet stats = ((OracleCallableStatement) displayParticipants).getCursor(3);

         if(!stats.next()){
             System.out.println("No participants found for this sport");
             return false;
         }

         System.out.printf( "%-20s%-25s%-30s%-10s%-12s", "Olympic Game", "Event Name", "Full Name", "Position", "Earned Medal");
         System.out.println();

         String olympicGame;
         String eventName;
         String fullName;
         int position;
         String medalTitle;

         //formats print statements
         do {
             olympicGame = stats.getString("olympic_game");
             eventName = stats.getString("event_name");
             fullName = stats.getString("full_name");
             position = stats.getInt("position");
             medalTitle = stats.getString("medal_title");
             System.out.printf( "%-20s%-25s%-30s%-10s%-12s", olympicGame, eventName, fullName, position, medalTitle);
             System.out.println();
         } while(stats.next());
         return true;
     }

     void logout() throws SQLException {
         PreparedStatement updateTimeStamp = dbcon.prepareStatement("UPDATE USER_ACCOUNT SET last_login =? WHERE user_id=?");

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

         updateTimeStamp.setDate(1, sqlDate);
         updateTimeStamp.setInt(2, currUserID);
         updateTimeStamp.executeQuery();

         System.out.println("Logout succeeded");
     }

     boolean registerTeam(int teamID, int eventID) throws SQLException {

         //validates existence of team id
         PreparedStatement checkValidTeam = dbcon.prepareStatement("SELECT * FROM TEAM WHERE team_id=?");
         checkValidTeam.setInt(1, teamID);
         ResultSet validTeam = checkValidTeam.executeQuery();
         if(!validTeam.next()) {
             System.out.println("Invalid team");
             return false;
         }

         //validates existence of event id
         PreparedStatement checkValidEvent = dbcon.prepareStatement("SELECT * FROM EVENT WHERE event_id=?");
         checkValidEvent.setInt(1, eventID);
         ResultSet validEvent = checkValidEvent.executeQuery();
         if(!validEvent.next()) {
             System.out.println("Invalid event");
             return false;
         }

        //inserts team and event into event_participation table and marks as enrolled
        PreparedStatement enrollTeam = dbcon.prepareStatement("INSERT INTO EVENT_PARTICIPATION(event_id, team_id, status) VALUES(?,?,'e')");
        enrollTeam.setInt(1,eventID);
        enrollTeam.setInt(2, teamID);
        enrollTeam.executeQuery();

        System.out.println("Team " + teamID + " has been registered for event " + eventID);
        return true;
     }

     boolean connectedAthletes(int participantID, int olympicID, int n) throws SQLException {
        if (n < 0 || n > 3) {
            System.out.println("Invalid entry for n, please try again");
            return false;
        }

         //validates existence of participant_id
         PreparedStatement getParticipant = dbcon.prepareStatement("SELECT * FROM PARTICIPANT WHERE PARTICIPANT_ID=?");
         getParticipant.setInt(1, participantID);
         ResultSet participantResult = getParticipant.executeQuery();
         if(!participantResult.next()) {
             System.out.println("Participant " + participantID + " was not found in the database, please try again");
             return false;
         }

         //validates existence of olympic_id
         PreparedStatement checkOlympic = dbcon.prepareStatement("SELECT * FROM OLYMPICS WHERE olympic_id=?");
         checkOlympic.setInt(1, olympicID);
         ResultSet validOlympic = checkOlympic.executeQuery();
         if(!validOlympic.next()) {
             System.out.println("Invalid Olympic");
             return false;
         }

         //stored procedure to display connections
         CallableStatement displayConnection = dbcon.prepareCall("BEGIN PROC_GET_CONNECTED(?,?,?); END;");
         displayConnection.setInt(1, participantID);
         displayConnection.setInt(2, n);
         displayConnection.registerOutParameter(3, OracleTypes.CURSOR);
         displayConnection.executeQuery();
         ResultSet stats = ((OracleCallableStatement) displayConnection).getCursor(3);

         if(!stats.next()){
             System.out.println("No connections found");
             return false;
         }

         System.out.printf( "%-30s%-30s", "Athlete", "Connected to");
         System.out.println();

         String athlete;
         String connection;

         //formats print statements
         do {
             athlete = stats.getString("full_name");
             connection = stats.getString("name");
             System.out.printf( "%-30s%-30s", athlete, connection);
             System.out.println();
         } while(stats.next());
         return true;
     }

     boolean addParticipant(String fname, String lname, String nationality, String birthplace, String dob) throws SQLException {

        //makes sure fname is present
        if(fname == null) {
            System.out.println("Please enter a first name");
            return false;
        }

        //makes sure lname is present
        if(lname == null) {
            System.out.println("Please enter a last name");
            return false;
        }

        //makes sure nationality is present
         if(nationality == null) {
             System.out.println("Please enter a nationality");
             return false;
         }

         //validates country name and gets associated id
         PreparedStatement getCountry = dbcon.prepareStatement("SELECT * FROM COUNTRY WHERE country=?");
         getCountry.setString(1, nationality);
         ResultSet countryResult = getCountry.executeQuery();
         if(!countryResult.next()) {
             System.out.println(nationality + " was not found in the database, please try again");
             return false;
         }
         
        //inserts new participant into participant table
        PreparedStatement addParticipant = dbcon.prepareStatement("INSERT INTO PARTICIPANT(fname, lname, nationality, birth_place, dob) VALUES(?,?,?,?,?)");
        addParticipant.setString(1, fname);
        addParticipant.setString(2, lname);
        addParticipant.setString(3, nationality);
        addParticipant.setString(4, birthplace);

        //converts dob string to sql Date
        java.sql.Date date;
        date =  valueOf(dob);
        addParticipant.setDate(5, date);
        addParticipant.executeQuery();
        System.out.println("Participant has successfully been added");
        return true;
     }

     boolean createTeam(String olympicCity, int olympicYear, String sport, String country, String teamName, int coachID) throws SQLException {
         int olympicID = 0;
         int sportID = 0;
         int countryID = 0;

         //validates olympic data and gets associated id
         PreparedStatement getOlympic = dbcon.prepareStatement("SELECT * FROM OLYMPICS WHERE EXTRACT(YEAR FROM opening_date) =? AND host_city =?");
         getOlympic.setInt(1, olympicYear);
         getOlympic.setString(2, olympicCity);
         ResultSet olympicResult = getOlympic.executeQuery();
         if(olympicResult.next())
             olympicID = olympicResult.getInt("olympic_ID");
         else {
             System.out.println(olympicCity + " " + olympicYear + " was not found in the database, please try again");
             return false;
         }

         //validates given coach id
         PreparedStatement getCoach = dbcon.prepareStatement("SELECT * FROM PARTICIPANT WHERE participant_id =?");
         getCoach.setInt(1, coachID);
         ResultSet coachResult = getCoach.executeQuery();
         if(!coachResult.next()) {
             System.out.println("Invalid coach ID, please try again");
             return false;
         }

         //validates sport name and gets associated id
         PreparedStatement getSport = dbcon.prepareStatement("SELECT * FROM SPORT WHERE sport_name=?");
         getSport.setString(1, sport);
         ResultSet sportResult = getSport.executeQuery();
         if(sportResult.next())
             sportID = sportResult.getInt("sport_ID");
         else {
             System.out.println(sport + " was not found in the database, please try again");
             return false;
         }

         //validates country name and gets associated id
         PreparedStatement getCountry = dbcon.prepareStatement("SELECT * FROM COUNTRY WHERE country=?");
         getCountry.setString(1, country);
         ResultSet countryResult = getCountry.executeQuery();
         if(countryResult.next())
             countryID = countryResult.getInt("country_ID");
        else {
             System.out.println(country + " was not found in the database, please try again");
             return false;
         }

        //inserts new team and links coach to team
         PreparedStatement addTeam = dbcon.prepareStatement("INSERT INTO TEAM(OLYMPICS_ID, TEAM_NAME, COUNTRY_ID, SPORT_ID, COACH_ID) VALUES(?,?,?,?,?) ");
         addTeam.setInt(1, olympicID);
         addTeam.setString(2, teamName);
         addTeam.setInt(3, countryID);
         addTeam.setInt(4, sportID);
         addTeam.setInt(5, coachID);
         ResultSet teamResult = addTeam.executeQuery();
         System.out.println("Team was successfully added");
         return true;
     }

     //adds participant to a given team
     boolean addTeamMember(int teamID, int participantID) throws SQLException {

        //validates existence of team_id
         PreparedStatement getTeam = dbcon.prepareStatement("SELECT * FROM TEAM WHERE TEAM_ID=?");
         getTeam.setInt(1, teamID);
         ResultSet teamResult = getTeam.executeQuery();
         if(!teamResult.next()) {
             System.out.println("Team " + teamID + " was not found in the database, please try again");
             return false;
         }

         //validates existence of participant_id
         PreparedStatement getParticipant = dbcon.prepareStatement("SELECT * FROM PARTICIPANT WHERE PARTICIPANT_ID=?");
         getParticipant.setInt(1, participantID);
         ResultSet participantResult = getParticipant.executeQuery();
         if(!participantResult.next()) {
             System.out.println("Participant " + participantID + " was not found in the database, please try again");
             return false;
         }

         //inserts participant_id and team_id into team_member table
         PreparedStatement addTeamMember = dbcon.prepareStatement("INSERT INTO TEAM_MEMBER(TEAM_ID, PARTICIPANT_ID) VALUES (?,?)");
         addTeamMember.setInt(1, teamID);
         addTeamMember.setInt(2, participantID);
         ResultSet result = addTeamMember.executeQuery();
         System.out.println("Team member added successfully!");
         return true;
     }

     //closes db connection and exits program
     void exit() throws SQLException {
        System.out.println("Goodbye");
        dbcon.close();
        System.exit(1);
     }
}
