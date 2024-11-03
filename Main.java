import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main {
    //ArrayLists to store multiple submissions
    private static ArrayList<String> jobSubmissions = new ArrayList<>();
    private static ArrayList<String> carOwnerSubmissions = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Vehicular Cloud Console");
        frame.setSize(480, 600);
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       List<Integer> jobDurations = new ArrayList<>(); 

        // Job Submitter Panel Setup and Information

        JPanel informationInputJobSubmitterPanel = new JPanel();
        informationInputJobSubmitterPanel.setLayout(new GridLayout(8, 2));

        JLabel clientIDLabel = new JLabel("Client ID: ");
        JTextField clientIDTextField = new JTextField(30);

        informationInputJobSubmitterPanel.add(clientIDLabel);
        informationInputJobSubmitterPanel.add(clientIDTextField);

        JLabel jobDurationLabel = new JLabel("Job Duration: ");
        JTextField jobDurationTextField = new JTextField(30);

        informationInputJobSubmitterPanel.add(jobDurationLabel);
        informationInputJobSubmitterPanel.add(jobDurationTextField );

        JLabel jobDeadlineLabel = new JLabel("Job Deadline: ");
        JTextField jobDeadlineTextField = new JTextField(30);

        informationInputJobSubmitterPanel.add(jobDeadlineLabel);
        informationInputJobSubmitterPanel.add(jobDeadlineTextField);

        // Job Submission Button

        JButton submitButton = new JButton("Submit");
        informationInputJobSubmitterPanel.add(new JLabel());
        informationInputJobSubmitterPanel.add(submitButton);


        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientID = clientIDTextField.getText();
                String jobDuration = jobDurationTextField.getText();
                String jobDeadline = jobDeadlineTextField.getText();

                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                
                try {
                	int jobDurationInt = Integer.parseInt(jobDuration);
                	jobDurations.add(jobDurationInt);
               

                try(FileWriter writer = new FileWriter("job_submitter_data.txt", true)){
                    writer.write("Client ID: " + clientID + "\n");
                    writer.write("Job Duration: " + jobDuration + "\n");
                    writer.write("Job Deadline: " + jobDeadline + "\n");
                    writer.write("Timestamp " + timestamp + "\n");
                }
                catch(IOException ex) {
                    ex.printStackTrace();
                }
                
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for job duration.");
                }

                //Store submission in list
                jobSubmissions.add("Client ID: " + clientID + ", Job Duration: " + jobDuration + ", Job Deadline: " + jobDeadline + ", Timestamp: " + timestamp);


                clientIDTextField.setText("");
                jobDurationTextField.setText("");
                jobDeadlineTextField.setText("");

            }
        });

        //Button to view job submissions
        JButton viewJobsButton = new JButton("View Job Submissions");
        informationInputJobSubmitterPanel.add(new JLabel());
        informationInputJobSubmitterPanel.add(viewJobsButton);

        viewJobsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, String.join("\n", jobSubmissions), "Job Submissions", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        informationInputJobSubmitterPanel.setVisible(false);

        // Car Owner Panel Setup and Information

        JPanel informationInputCarOwnerPanel = new JPanel();

        informationInputCarOwnerPanel.setLayout(new GridLayout(6, 2));

        JLabel ownerIDLabel = new JLabel("Owner ID: ");
        JTextField ownerIDTextField = new JTextField(30);

        informationInputCarOwnerPanel.add(ownerIDLabel);
        informationInputCarOwnerPanel.add(ownerIDTextField);

        JLabel vinLabel = new JLabel("VIN Info: ");
        JTextField vinTextField = new JTextField(30);

        informationInputCarOwnerPanel.add(vinLabel);
        informationInputCarOwnerPanel.add(vinTextField);

        JLabel residencyLabel = new JLabel("Residency Time: ");
        JTextField residencyTextField = new JTextField(30);

        informationInputCarOwnerPanel.add(residencyLabel);
        informationInputCarOwnerPanel.add(residencyTextField);

        // Car Owner Submission Button

        JButton carOwnerSubmitButton = new JButton("Submit");
        informationInputCarOwnerPanel.add(new JLabel());
        informationInputCarOwnerPanel.add(carOwnerSubmitButton);


        carOwnerSubmitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String ownerID = ownerIDTextField.getText();
                String vinInfo = vinTextField.getText();
                String residencyTime = residencyTextField.getText();

                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                try(FileWriter writer = new FileWriter("car_owner_data.txt",true)){
                    writer.write("Owner ID: " + ownerID + "\n");
                    writer.write("Vin Info: " + vinInfo + "\n");
                    writer.write("Residency Time: " + residencyTime + "\n");
                    writer.write("Timestamp " + timestamp + "\n");
                }
                catch(IOException ex) {
                    ex.printStackTrace();
                }

                //Store submission in list
                carOwnerSubmissions.add("Owner ID: " + ownerID + ", VIN Info: " + vinInfo + ", Residency Time: " + residencyTime + ", Timestamp: " + timestamp);

                ownerIDTextField.setText("");
                vinTextField.setText("");
                residencyTextField.setText("");
            }

        });

        //Button to view car owner submissions
        JButton viewOwnersButton = new JButton("View Car Owner Submissions");
        informationInputCarOwnerPanel.add(new JLabel());
        informationInputCarOwnerPanel.add(viewOwnersButton);

        viewOwnersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, String.join("\n", carOwnerSubmissions), "Car Owner Submissions", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        informationInputCarOwnerPanel.setVisible(false);
        
        //Back Button
        JButton backButton = new JButton("Back");

        // log in panel

        JPanel logInPanel = new JPanel(new GridLayout(4, 2));

        JLabel currentSelectionButton = new JLabel("");
        
        
        logInPanel.setVisible(false);

        //usernames and passwords
        ArrayList<Client> clientArray = new ArrayList<>();
        clientArray.add(new Client("user1","password",00001,"John Doe", 0.00));
        ArrayList<Owner> ownerArray = new ArrayList<>();
        ownerArray.add(new Owner("user2","password",00002,"Jane Doe",0.00,
                new Car("Atlas Cross Sport","Volkswagon", 2024,"1HGBH41JXMN109186","MM52SAM","gas", true)));

        JLabel userLabel = new JLabel("username: ");
        JTextField userTextField = new JTextField(30);

        logInPanel.add(userLabel);
        logInPanel.add(userTextField);

        JLabel passwordLabel = new JLabel("password: ");
        JPasswordField passwordTextField = new JPasswordField(30);

        logInPanel.add(passwordLabel);
        logInPanel.add(passwordTextField);
        
        backButton.setVisible(true);

        int[] userArrayPosition = new int[1];

        //job submitter login
        JButton clientJobSubmitterButton = new JButton("Job Submitter Login");
        logInPanel.add(clientJobSubmitterButton);

        clientJobSubmitterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(currentSelectionButton.getText());
                String username = userTextField.getText();
                String password = passwordTextField.getText();
                for (int i = 0; i < clientArray.size(); i++) {
                    if (username.equals(clientArray.get(i).getUsername()) && password.equals(clientArray.get(i).getPassword())) {
                        currentSelectionButton.setText("Job Submitter");
                        logInPanel.setVisible(false);
                        informationInputJobSubmitterPanel.setVisible(true);
                        userArrayPosition[0] = i;
                        System.out.println(clientArray);
                        System.out.println(userArrayPosition[0]);
                    }
                }
            }
        });
        
        

        //car owner login

        JButton clientCarOwnerButton = new JButton("Car Owner Login");
        logInPanel.add(clientCarOwnerButton);
        
        

        clientCarOwnerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(currentSelectionButton.getText());
                String username = userTextField.getText();
                String password = new String(passwordTextField.getPassword()); 
                for (int i = 0; i < ownerArray.size(); i++) {
                    if (username.equals(ownerArray.get(i).getUsername()) && password.equals(ownerArray.get(i).getPassword())) {
                        currentSelectionButton.setText("Car Owner");
                        logInPanel.setVisible(false);
                        informationInputCarOwnerPanel.setVisible(true);
                        userArrayPosition[0] = i;
                    }
                }
            }
        });
        
       

        // create account
        JPanel createAccountPanel = new JPanel(new GridLayout(13,2));
        createAccountPanel.setVisible(false);

        JLabel userCreateLabel = new JLabel("username: ");
        JTextField userCreateTextField = new JTextField(30);

        createAccountPanel.add(userCreateLabel);
        createAccountPanel.add(userCreateTextField);

        JLabel passwordCreateLabel = new JLabel("password: ");
        JPasswordField passwordCreateTextField = new JPasswordField(30);

        createAccountPanel.add(passwordCreateLabel);
        createAccountPanel.add(passwordCreateTextField);

        JLabel fullnameCreateLabel = new JLabel("full name: ");
        JTextField fullnameCreateTextField = new JTextField(30);

        createAccountPanel.add(fullnameCreateLabel);
        createAccountPanel.add(fullnameCreateTextField);

        JLabel carModelCreateLabel = new JLabel("car model: ");
        JTextField carModelCreateTextField = new JTextField(30);

        createAccountPanel.add(carModelCreateLabel);
        createAccountPanel.add(carModelCreateTextField);

        JLabel carMakeCreateLabel = new JLabel("car make: ");
        JTextField carMakeCreateTextField = new JTextField(30);

        createAccountPanel.add(carMakeCreateLabel);
        createAccountPanel.add(carMakeCreateTextField);

        JLabel carYearCreateLabel = new JLabel("car year: ");
        JTextField carYearCreateTextField = new JTextField(30);

        createAccountPanel.add(carYearCreateLabel);
        createAccountPanel.add(carYearCreateTextField);

        JLabel carVinCreateLabel = new JLabel("car vin: ");
        JTextField carVinCreateTextField = new JTextField(30);

        createAccountPanel.add(carVinCreateLabel);
        createAccountPanel.add(carVinCreateTextField);

        JLabel carPlateNumberCreateLabel = new JLabel("car plate number: ");
        JTextField carPlateNumberCreateTextField = new JTextField(30);

        createAccountPanel.add(carPlateNumberCreateLabel);
        createAccountPanel.add(carPlateNumberCreateTextField);

        JLabel carTypeCreateLabel = new JLabel("car type: ");
        JTextField carTypeCreateTextField = new JTextField(30);

        createAccountPanel.add(carTypeCreateLabel);
        createAccountPanel.add(carTypeCreateTextField);


        createAccountPanel.add(currentSelectionButton);
   


        //job submitter create account
        JButton clientJobSubmitterCreationButton = new JButton("Job Submitter Create Account");
        createAccountPanel.add(clientJobSubmitterCreationButton);

        clientJobSubmitterCreationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userCreateTextField.getText();
                String password = passwordCreateTextField.getText();
                String fullname = fullnameCreateTextField.getText();
                clientArray.add(new Client(username,password,clientArray.size() + ownerArray.size() + 1,fullname, 0.00));
                currentSelectionButton.setText("Account Created");
                createAccountPanel.setVisible(false);
                informationInputJobSubmitterPanel.setVisible(true);
                userArrayPosition[0] = clientArray.size() - 1;
            }
        });

        //owner create account
        JButton clientCarOwnerCreationButton = new JButton("Car Owner Create Account");
        createAccountPanel.add(clientCarOwnerCreationButton);

        clientCarOwnerCreationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userCreateTextField.getText();
                String password = passwordCreateTextField.getText();
                String fullname = fullnameCreateTextField.getText();
                String carModel = carModelCreateTextField.getText();
                String carMake = carMakeCreateTextField.getText();
                int carYear = Integer.parseInt(carYearCreateTextField.getText());
                String carVin = carVinCreateTextField.getText();
                String carPlateNumber = carPlateNumberCreateTextField.getText();
                String carType = carTypeCreateTextField.getText();

                ownerArray.add(new Owner(username, password,clientArray.size() + ownerArray.size() + 1, fullname, 0.00, new Car(carModel, carMake, carYear, carVin, carPlateNumber, carType, true)));
                currentSelectionButton.setText("Account Created");
                createAccountPanel.setVisible(false);
                informationInputCarOwnerPanel.setVisible(true);
                userArrayPosition[0] = ownerArray.size() - 1;
            }
        });

        createAccountPanel.add(currentSelectionButton);
        

        // Home page
        JPanel homePagePanel = new JPanel(new GridLayout(5,1));

        JLabel homePageLabel = new JLabel("Welcome to the Vehicular Cloud Console");
        JLabel homePageInfoLabel = new JLabel("Connecting Jobs to Cars since 2024");

        homePagePanel.add(homePageLabel);
        homePagePanel.add(homePageInfoLabel);

        JButton logInPageButton = new JButton("Log In");
        homePagePanel.add(logInPageButton);

        logInPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePagePanel.setVisible(false);
                logInPanel.setVisible(true);
                logInPanel.add(backButton);
            }
        });
        
        

        JButton createAccountPageButton = new JButton("Create Account");
        homePagePanel.add(createAccountPageButton);

        createAccountPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePagePanel.setVisible(false);
                createAccountPanel.setVisible(true);
                createAccountPanel.add(backButton);
            }
        });

        homePagePanel.setVisible(true);
        frame.add(homePagePanel);
        
      
      // Back Button Setup  
        backButton.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		logInPanel.setVisible(false);
        		createAccountPanel.setVisible(false);
        		homePagePanel.setVisible(true);
        	}
        });
        


        //client profile page
        JPanel clientProfilePanel = new JPanel();
        clientProfilePanel.setLayout(new GridLayout(6,2));

        JLabel clientNameLabel = new JLabel("Name: ");
        JLabel clientName = new JLabel(clientArray.get(userArrayPosition[0]).getFullName());

        clientProfilePanel.add(clientNameLabel);
        clientProfilePanel.add(clientName);

        JLabel clientUsernameLabel = new JLabel("Username: ");
        JLabel clientUsername = new JLabel(clientArray.get(userArrayPosition[0]).getUsername());

        clientProfilePanel.add(clientUsernameLabel);
        clientProfilePanel.add(clientUsername);

        JLabel clientProfileIDLabel = new JLabel("ID: ");
        JLabel clientProfileID = new JLabel(String.valueOf(clientArray.get(userArrayPosition[0]).getID()));

        clientProfilePanel.add(clientProfileIDLabel);
        clientProfilePanel.add(clientProfileID);

        JLabel clientBalanceLabel = new JLabel("Account Balance: ");
        JLabel clientBalance = new JLabel(String.valueOf(clientArray.get(userArrayPosition[0]).getBalance()));

        clientProfilePanel.add(clientBalanceLabel);
        clientProfilePanel.add(clientBalance);

        JButton clientProfileButton = new JButton("Profile");
        informationInputJobSubmitterPanel.add(clientProfileButton);

        clientProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientName.setText(clientArray.get(userArrayPosition[0]).getFullName());
                clientUsername.setText(clientArray.get(userArrayPosition[0]).getUsername());
                clientProfileID.setText(String.valueOf(clientArray.get(userArrayPosition[0]).getID()));
                clientBalance.setText(String.valueOf(clientArray.get(userArrayPosition[0]).getBalance()));

                clientProfilePanel.setVisible(true);
                informationInputJobSubmitterPanel.setVisible(false);
            }
        });
        clientProfilePanel.setVisible(false);

        //back to job submission page
        JButton returnToJobPage = new JButton("Home");
        clientProfilePanel.add(returnToJobPage);

        returnToJobPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientProfilePanel.setVisible(false);
                informationInputJobSubmitterPanel.setVisible(true);
            }
        });
        
        // Calculate Total Job Duration
        JButton calculateButton = new JButton("Total Job Duration");
        //informationInputJobSubmitterPanel.add(calculateButton);
        
        calculateButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		int totalDuration = jobDurations.stream().mapToInt(Integer::intValue).sum();
        		JOptionPane.showMessageDialog(frame, "Total Job Duration: " + totalDuration);
        		
        	}
        });
        
        
        //car owner profile page
        JPanel ownerProfilePanel = new JPanel();
        ownerProfilePanel.setLayout(new GridLayout(7,2));

        JLabel ownerNameLabel = new JLabel("Name: ");
        JLabel ownerName = new JLabel(ownerArray.get(userArrayPosition[0]).getFullName());

        ownerProfilePanel.add(ownerNameLabel);
        ownerProfilePanel.add(ownerName);

        JLabel ownerUsernameLabel = new JLabel("Username: ");
        JLabel ownerUsername = new JLabel(ownerArray.get(userArrayPosition[0]).getUsername());

        ownerProfilePanel.add(ownerUsernameLabel);
        ownerProfilePanel.add(ownerUsername);

        JLabel ownerProfileIDLabel = new JLabel("ID: ");
        JLabel ownerProfileID = new JLabel(String.valueOf(ownerArray.get(userArrayPosition[0]).getID()));

        ownerProfilePanel.add(ownerProfileIDLabel);
        ownerProfilePanel.add(ownerProfileID);

        JLabel ownerBalanceLabel = new JLabel("Account Balance: ");
        JLabel ownerBalance = new JLabel(String.valueOf(ownerArray.get(userArrayPosition[0]).getBalance()));

        ownerProfilePanel.add(ownerBalanceLabel);
        ownerProfilePanel.add(ownerBalance);

        JLabel ownerCarLabel = new JLabel("Car: ");
        JLabel carInfo = new JLabel(ownerArray.get(userArrayPosition[0]).getCars().toString());

        ownerProfilePanel.add(ownerCarLabel);
        ownerProfilePanel.add(carInfo);

        JButton ownerProfileButton = new JButton("Profile");
        informationInputCarOwnerPanel.add(ownerProfileButton);

        ownerProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ownerName.setText(ownerArray.get(userArrayPosition[0]).getFullName());
                ownerUsername.setText(ownerArray.get(userArrayPosition[0]).getUsername());
                ownerProfileID.setText(String.valueOf(ownerArray.get(userArrayPosition[0]).getID()));
                ownerBalance.setText(String.valueOf(ownerArray.get(userArrayPosition[0]).getBalance()));
                carInfo.setText(ownerArray.get(userArrayPosition[0]).getCars().toString());

                ownerProfilePanel.setVisible(true);
                informationInputCarOwnerPanel.setVisible(false);
            }
        });
        ownerProfilePanel.setVisible(false);

        //back to car owner page
        JButton returnToCarPage = new JButton("Home");
        ownerProfilePanel.add(returnToCarPage);

        returnToCarPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ownerProfilePanel.setVisible(false);
                informationInputCarOwnerPanel.setVisible(true);
            }
        });

        //job submitter log out button
        JButton jobSubmitterLogoutButton = new JButton("Log Out");
        informationInputJobSubmitterPanel.add(jobSubmitterLogoutButton);
        informationInputJobSubmitterPanel.add(calculateButton);

        jobSubmitterLogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePagePanel.setVisible(true);
                informationInputJobSubmitterPanel.setVisible(false);
                currentSelectionButton.setText("");
            }
        });

        //car owner log out button
        JButton carOwnerLogoutButton = new JButton("Log Out");
        informationInputCarOwnerPanel.add(carOwnerLogoutButton);

        carOwnerLogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePagePanel.setVisible(true);
                informationInputCarOwnerPanel.setVisible(false);
                currentSelectionButton.setText("");
            }
        });
        

        frame.add(logInPanel);
        frame.add(createAccountPanel);

        frame.add(informationInputJobSubmitterPanel);
        frame.add(informationInputCarOwnerPanel);
        frame.add(clientProfilePanel);
        frame.add(ownerProfilePanel);

        frame.setVisible(true);

        System.out.println("Hello world!");
    }
}
