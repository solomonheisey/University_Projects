/**
 * This class is the driver class for Olympic-Games. It does not take in user input but instead provides
 * insight on parameters for method calls and return types.
 *
 * @author Solomon Heisey
 * @version 1.0
 * @since 2020-04-16
 *
 */

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Driver {

    public static void main(String[] args) throws SQLException {
        Olympic olympic = new Olympic();

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Create User:");

        /**
         * This method is used to add a new user to the system
         * @param username This is the unique username for the new user
         * @param passkey This is the passkey for the new user
         * @param role_ID This is the role ID of the new user
         * @return boolean This returns where or not the action has been successfully executed
         */
        olympic.createUser("test", "testKey", 1);
        PreparedStatement testAddUser = olympic.dbcon.prepareStatement("SELECT * FROM USER_ACCOUNT WHERE username=?");
        testAddUser.setString(1, "test");
        ResultSet resultSet = testAddUser.executeQuery();
        if(resultSet.next()) {

            System.out.printf( "%-9s%-22s%-22s%-9s%-22s", "user_id", "username", "passkey", "role_id", "last_login");
            System.out.println();

            int userID;
            String username;
            String passkey;
            int roleID;
            Date lastLogin;

            userID = resultSet.getInt("user_id");
            username = resultSet.getString("username");
            passkey = resultSet.getString("passkey");
            roleID = resultSet.getInt("role_ID");
            lastLogin = resultSet.getDate("last_login");
            System.out.printf( "%-9s%-22s%-22s%-9s%-22s", userID, username, passkey, roleID, lastLogin);
            System.out.println();
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Login:");

        /**
         * This method is used for a user to log into the system
         * @param username This is the username of the user
         * @param passkey This is the passkey for the user
         * @return boolean This returns where or not the action has been successfully executed
         */
        olympic.login("test", "testKey");

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Top k Athletes: ");

        /**
         * This method is used to display the top k athletes based on points for a given olympic it displays the number
         * of medals won by each athlete and sorts based on points and ranks accordingly.
         * @param olympicID This is the target olympics where the data should be selected from
         * @param k This is the number of athletes to display
         * @return boolean This returns where or not the action has been successfully executed
         */
        olympic.topkAthletes(1,5);

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Country Ranking");

        /**
         * This method is used to display all participating countries, the first year the country participate in the Olympics
         * along with the number of gold, silver, and bronze medals in a descending order of their rank. The rank is computed
         * based on the points associated with each medal.
         * @param olympic_id Which olympic to rank countries from
         * @return boolean This returns where or not the action has been successfully executed
         */
        olympic.countryRanking(1);

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Display Sport:");

        /**
         * This method displays the Olympic year it was added, events of that sport, gender, the medal winners, and their countries
         * @param sportName Which sport to run the query on
         * @return boolean This returns where or not the action has been successfully executed
         */

        olympic.displaySport("Tennis");

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Display Event:");

        /**
         * This method displays the Olympic game, event name, participant, and the position along with the earned medal
         * @param olympicCity Host Olympic city
         * @param olympicYear The year the desired Olympic took place in
         * @param eventID Event to get statistics from
         * @return boolean This returns where or not the action has been successfully executed
         */

        olympic.displayEvent("Athens", 2004, 1007);

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Connected Athletes:");

        /**
         * This method finds all the athletes who are connected to this athlete based on the participation in the last
         * n+1 games
         * @param participantID ID of the target athlete
         * @param olympicID ID of the target olympics
         * @param n Degrees of separation from target athlete
         * @return boolean This returns where or not the action has been successfully executed
         */

        olympic.connectedAthletes(1,1,1);

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Create Team:");

        /**
         * This method is used to add a new team to the system
         * @param olympicCity This is the city the team will compete in
         * @param olympicYear This is the year the team will compete in
         * @param sport This is the sport the team is competing in
         * @param country This is the country the team is competing for
         * @param coachID This is the coach of the given team
         * @return boolean This returns where or not the action has been successfully executed
         */
        olympic.createTeam("Athens", 2004, "Weightlifting", "United States", "TestTeam", 1037);
        PreparedStatement testCreateTeam =  olympic.dbcon.prepareStatement("SELECT * FROM TEAM WHERE team_name =?");
        testCreateTeam.setString(1, "TestTeam");
        ResultSet ctrs = testCreateTeam.executeQuery();

        System.out.printf( "%-9s%-13s%-30s%-12s%-10s%-10s", "team_id", "olympics_id", "team_name", "country_id", "sport_id", "coach_id");
        System.out.println();

        int teamID = 0;
        int olympicsID;
        String teamName;
        int countryID;
        int sportID;
        int coachID;

        while(ctrs.next()) {
            teamID = ctrs.getInt("team_id");
            olympicsID = ctrs.getInt("olympics_id");
            teamName = ctrs.getString("team_name");
            countryID = ctrs.getInt("country_id");
            sportID = ctrs.getInt("sport_id");
            coachID = ctrs.getInt("coach_id");
            System.out.printf( "%-9s%-13s%-30s%-12s%-10s%-10s", teamID, olympicsID, teamName, countryID, sportID, coachID);
            System.out.println();
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Register Team: ");

        /**
         * This method is used to register a team for an existing event.
         * @param teamID This is the team to be enrolled for the event
         * @param eventID This is the event for the team to be enrolled in
         * @return boolean This returns where or not the action has been successfully executed
         */
        olympic.registerTeam(teamID, 2002);

        PreparedStatement testRegisterTeam = olympic.dbcon.prepareStatement("SELECT * FROM EVENT_PARTICIPATION WHERE EVENT_ID=? AND team_ID =?");
        testRegisterTeam.setInt(1, 2002);
        testRegisterTeam.setInt(2, teamID);
        ResultSet resultSet1 = testRegisterTeam.executeQuery();

        if(resultSet1.next()) {

            System.out.printf( "%-10s%-9s%-8s", "event_id", "team_id", "status");
            System.out.println();

            int eventID = resultSet1.getInt("event_id");
            char status = resultSet1.getString("status").charAt(0);
            System.out.printf( "%-10s%-9s%-8s", eventID, teamID, status);
            System.out.println();
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Add Participant: ");

        /**
         * This method is used to create a new participant
         * @param fname This is the first name of the participant
         * @param lname This is the last name of the participant
         * @param nationality This is the nationality of the participant
         * @param birthplace This is where the participant was born
         * @param dob This is the date of birth for the participant
         * @return boolean This returns where or not the action has been successfully executed
         */
        olympic.addParticipant("testParticipant", "participant", "India", "Russia", "1996-05-07");

        PreparedStatement testAddParicipant = olympic.dbcon.prepareStatement("SELECT * FROM PARTICIPANT WHERE fname=? AND lname=?");
        testAddParicipant.setString(1, "testParticipant");
        testAddParicipant.setString(2, "participant");
        ResultSet resultSet2 = testAddParicipant.executeQuery();

        int participantID =0;
        if(resultSet2.next()) {

            System.out.printf( "%-16s%-22s%-22s%-22s%-22s%-14s", "participant_id", "fname", "lname", "nationality", "birth_place", "dob");
            System.out.println();

            participantID = resultSet2.getInt("participant_id");
            String fname = resultSet2.getString("fname");
            String lname = resultSet2.getString("lname");
            String nationality = resultSet2.getString("nationality");
            String birthPlace = resultSet2.getString("birth_place");
            Date dob = resultSet2.getDate("dob");
            System.out.printf("%-16s%-22s%-22s%-22s%-22s%-14s", participantID, fname, lname, nationality, birthPlace, dob);
            System.out.println();
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Add Team Member: ");

        /**
         * This method is used to add a participant to a team
         * @param teamID This is the id of the team that the participant should be added to
         * @param participantID This is the id of the participant being added to the given team
         * @return boolean This returns where or not the action has been successfully executed
         */

        olympic.addTeamMember(teamID, participantID);

        PreparedStatement testAddTeamMember = olympic.dbcon.prepareStatement("SELECT * FROM TEAM_MEMBER WHERE PARTICIPANT_ID=? AND TEAM_ID =?");
        testAddTeamMember.setInt(1, participantID);
        testAddTeamMember.setInt(2, teamID);
        ResultSet resultSet3 = testAddTeamMember.executeQuery();

        if(resultSet3.next()) {

            System.out.printf("%-16s%-8s", "participant_id", "team_id");
            System.out.println();

            int tempPart = resultSet3.getInt("participant_id");
            int tempTeam = resultSet3.getInt("team_id");
            System.out.printf("%-16s%-8s", tempPart, tempTeam);
            System.out.println();
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Create Event: ");

        /**
         * This method is used to add a new event to the system
         * @param sportID ID of the sport for the new event
         * @param venueID ID of the venue for the new event
         * @param date date event will take place
         * @param gender gender of the event
         * @return boolean This returns where or not the action has been successfully executed
         */

        olympic.createEvent(4, 2002, "2004-08-14", 'm');

        PreparedStatement testCreateEvent = olympic.dbcon.prepareStatement("SELECT * FROM EVENT WHERE SPORT_ID=? AND VENUE_ID=? AND GENDER=?");
        testCreateEvent.setInt(1, 4);
        testCreateEvent.setInt(2, 2002);
        testCreateEvent.setInt(3, 1);
        ResultSet createEvent = testCreateEvent.executeQuery();

        int eventID = 0;
        if(createEvent.next()) {

            System.out.printf("%-10s%-10s%-10s%-8s%-20s", "event_id", "sport_id", "venue_id", "gender", "event_time");
            System.out.println();

            eventID = createEvent.getInt("event_id");
            sportID = createEvent.getInt("sport_id");
            int venueID = createEvent.getInt("venue_id");
            int gender = createEvent.getInt("gender");
            Date eventTime =createEvent.getDate("event_time");

            char temp = (gender == 1 ? 'm' : 'f');
            System.out.printf("%-10s%-10s%-10s%-8s%-20s", eventID, sportID, venueID, temp, eventTime);
            System.out.println();
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Add Event Outcome: ");

        /**
         * This method is used to add a new event outcome to the Olympics
         * @param olympicCity city of olympic games
         * @param olympicYear year of olympic games
         * @param eventID ID of given event
         * @param participantID ID of given participant
         * @param teamID ID of given team
         * @param position position of participant in event
         * @return boolean This returns where or not the action has been successfully executed
         */

        olympic.addEventOutcome("Athens", 2004, 2002, participantID, teamID, 2);

        PreparedStatement testAddEventOutcome = olympic.dbcon.prepareStatement("SELECT * FROM SCOREBOARD WHERE OLYMPICS_ID=? AND EVENT_ID=? AND TEAM_ID=? AND PARTICIPANT_ID=?");
        testAddEventOutcome.setInt(1, 4);
        testAddEventOutcome.setInt(2, 2002);
        testAddEventOutcome.setInt(3, teamID);
        testAddEventOutcome.setInt(4, participantID);
        ResultSet addEvent = testAddEventOutcome.executeQuery();

        if(addEvent.next()) {

            System.out.printf("%-14s%-10s%-9s%-16s%-10s%-10s", "olympics_id", "event_id", "team_id", "participant_id", "position", "medal_id");
            System.out.println();

            olympicsID = addEvent.getInt("olympics_id");
            int tempEventID = addEvent.getInt("event_id");
            teamID = addEvent.getInt("team_id");
            participantID = addEvent.getInt("participant_id");
            int position = addEvent.getInt("position");
            int medal_id = addEvent.getInt("medal_id");

            System.out.printf("%-14s%-10s%-9s%-16s%-10s%-10s", olympicsID, tempEventID, teamID, participantID, position, medal_id);
            System.out.println();
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Drop Team Member: ");

        /**
         * This method is used to remove a participant from competition
         * @param participantID This is the id of the participant being dropped
         * @return boolean This returns where or not the action has been successfully executed
         */

        olympic.dropTeamMember(participantID);

        PreparedStatement testDropTeamMember = olympic.dbcon.prepareStatement("SELECT * FROM PARTICIPANT WHERE PARTICIPANT_ID=?");
        testDropTeamMember.setInt(1, participantID);
        ResultSet verifyDeleted = testDropTeamMember.executeQuery();

        if(!verifyDeleted.next())
            System.out.println("No matching data. Participant has been deleted from the system.");

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Logout:");

        /**
         * This method is used for a user to log out of the system, it also updates the last_login attribute to the current time
         */
        olympic.logout();
        PreparedStatement testLogout = olympic.dbcon.prepareStatement("SELECT * FROM USER_ACCOUNT WHERE username=?");
        testLogout.setString(1, "test");
        ResultSet logout = testLogout.executeQuery();

        if(logout.next()) {

            System.out.printf( "%-9s%-22s%-22s%-9s%-20s", "user_id", "username", "passkey", "role_id", "last_login");
            System.out.println();

            int userID = logout.getInt("user_id");
            String username = logout.getString("username");
            String passkey = logout.getString("passkey");
            int roleID = logout.getInt("role_id");
            Date last_login = logout.getDate("last_login");

            System.out.printf( "%-9s%-22s%-22s%-9s%-20s", userID, username, passkey, roleID, last_login);
            System.out.println();
        }

        /**
         * This statement simply cleans up db and removes event that was added earlier
         */
        PreparedStatement removeEvent = olympic.dbcon.prepareStatement("DELETE FROM EVENT WHERE EVENT_ID=?");
        removeEvent.setInt(1, eventID);
        ResultSet temp = removeEvent.executeQuery();

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Drop User: ");

        /**
         * This method is used to drop any user in the system. If the user is the current user then they are deleted
         * and the program exits.
         * @param username This is the username of the target user
         * @return boolean This returns where or not the action has been successfully executed
         */
        olympic.dropUser("test");
        PreparedStatement testDropUser = olympic.dbcon.prepareStatement("SELECT * FROM USER_ACCOUNT WHERE username=?");
        testDropUser.setString(1, "test");
        ResultSet rs = testDropUser.executeQuery();
        if(!rs.next())
            System.out.println("No matching data. User has been deleted from the system.");

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Exit: ");

        /**
         * This method is used to exit the program. It drops db connection, and exits program
         */
        olympic.exit();
    }
}
