import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


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

        JLabel jobDurationLabel = new JLabel("Job Duration (hrs): ");
        JTextField jobDurationTextField = new JTextField(30);

        informationInputJobSubmitterPanel.add(jobDurationLabel);
        informationInputJobSubmitterPanel.add(jobDurationTextField );

        JLabel jobDeadlineLabel = new JLabel("Job Deadline (hrs): ");
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
                    writer.write("Job Duration (hrs): " + jobDuration + "\n");
                    writer.write("Job Deadline (hrs): " + jobDeadline + "\n");
                    writer.write("Timestamp " + timestamp + "\n");
                }
                catch(IOException ex) {
                    ex.printStackTrace();
                }
                
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for job duration.");
                }

                //Store submission in list
                jobSubmissions.add("Client ID: " + clientID + ", Job Duration (hrs): " + jobDuration + ", Job Deadline (hrs): " + jobDeadline + ", Timestamp: " + timestamp);


                clientIDTextField.setText("");
                jobDurationTextField.setText("");
                jobDeadlineTextField.setText("");

                //send to server, client socket
                try {
                    ClientSocketManager clientConnection = new ClientSocketManager();
                    clientConnection.write("Client ID: " + clientID + ", Job Duration (hrs): " + jobDuration + ", Job Deadline (hrs): " + jobDeadline + ", Timestamp: " + timestamp);
                    clientConnection.start();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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

        JLabel residencyLabel = new JLabel("Residency Time (hrs): ");
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
                    writer.write("Residency Time (hrs): " + residencyTime + "\n");
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

        //cloud controller panel
        JPanel cloudControllerPanel = new JPanel(new GridLayout(7,1));
        cloudControllerPanel.setVisible(false);

        //Calculate Job Completion Times
        JButton calculateCompletionButton = new JButton("Calculate Job Completion Time");
        cloudControllerPanel.add(calculateCompletionButton);

        calculateCompletionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> completionTimes = new ArrayList<>();
                int totalDuration = 0;

                //Calculate the completion time for each job in FIFO order
                for (int duration : jobDurations) {
                    totalDuration += duration;
                    completionTimes.add(totalDuration);
                }

                //Format and display the completion times
                String message = "Job Completion Times (hrs): " + completionTimes.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));
                JOptionPane.showMessageDialog(frame, message);
            }
        });

        // Calculate Total Job Duration
        JButton calculateButton = new JButton("Total Job Duration");
        cloudControllerPanel.add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalDuration = jobDurations.stream().mapToInt(Integer::intValue).sum();
                //Convert totalDuration to hours and format the message
                String message = String.format("Total Job Duration: %d hr%s", totalDuration, totalDuration == 1 ? "" : "s");
                JOptionPane.showMessageDialog(frame, message);
            }
        });

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
        JButton clientJobSubmitterLoginButton = new JButton("Job Submitter Login");
        logInPanel.add(clientJobSubmitterLoginButton);

        clientJobSubmitterLoginButton.addActionListener(new ActionListener() {
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
                    }
                }
            }
        });

        //car owner login
        JButton clientCarOwnerLoginButton = new JButton("Car Owner Login");
        logInPanel.add(clientCarOwnerLoginButton);

        clientCarOwnerLoginButton.addActionListener(new ActionListener() {
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

        //cloud controller log in
        JButton clientCloudControllerLoginButton = new JButton("Cloud Controller Login");
        logInPanel.add(clientCloudControllerLoginButton);

        clientCloudControllerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(currentSelectionButton.getText());
                String username = userTextField.getText();
                String password = passwordTextField.getText();
                if(username.equals("user") && password.equals("password")){
                    currentSelectionButton.setText("Cloud Controller");
                    cloudControllerPanel.setVisible(true);
                    logInPanel.setVisible(false);
                }
            }
        });
        
     // Button to view job submissions in Cloud Controller
        JButton viewJobsButtonCC = new JButton("Accept or Reject Job Submissions");
        cloudControllerPanel.add(new JLabel());
        cloudControllerPanel.add(viewJobsButtonCC);

        viewJobsButtonCC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Holds job submissions with Accept/Reject buttons
                JPanel jobPanel = new JPanel();
                jobPanel.setLayout(new BoxLayout(jobPanel, BoxLayout.Y_AXIS)); 
                
                // Loops through the job submission
                for (int i = 0; i < jobSubmissions.size(); i++) {
                    final int index = i;  
                    String job = jobSubmissions.get(i);  

                    
                    JPanel singleJobPanel = new JPanel();
                    singleJobPanel.setLayout(new BoxLayout(singleJobPanel, BoxLayout.X_AXIS)); // Layout for job info and buttons

                    // Displays the job information
                    singleJobPanel.add(new JLabel(job));

                    // Create the Accept and Reject buttons
                    JButton acceptButton = new JButton("Accept");
                    JButton rejectButton = new JButton("Reject");

                  
                    acceptButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Writes Accepted Jobs to New Text File
                            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            try (FileWriter writer = new FileWriter("accepted_jobs.txt", true)) {
                                writer.write("Accepted Job: " + job + "\n");
                                writer.write("Timestamp: " + timestamp + "\n");
                                writer.write("---------------------------\n");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            
                            JOptionPane.showMessageDialog(frame, "Job Accepted: " + job, "Job Status", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });

                    
                    rejectButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Removes Rejected job from the Job Submission List
                        	jobDurations.remove(index);
                            jobSubmissions.remove(index);
                            JOptionPane.showMessageDialog(frame, "Job has been rejected: " + job, "Job Status", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });

                  
                    singleJobPanel.add(acceptButton);
                    singleJobPanel.add(rejectButton);

                    
                    jobPanel.add(singleJobPanel);
                }

                
                JOptionPane.showMessageDialog(frame, jobPanel, "Job Submissions", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Button to view job submissions in Cloud Controller
        JButton viewCarsButtonCC = new JButton("Accept or Reject Car Submissions");
        cloudControllerPanel.add(new JLabel());
        cloudControllerPanel.add(viewCarsButtonCC);

        viewCarsButtonCC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Holds car submissions with Accept/Reject buttons
                JPanel carPanel = new JPanel();
                carPanel.setLayout(new BoxLayout(carPanel, BoxLayout.Y_AXIS)); 
                
                // Loops through the car submission
                for (int i = 0; i < carOwnerSubmissions.size(); i++) {
                    final int index = i;  
                    String car = carOwnerSubmissions.get(i);  

                    
                    JPanel singleCarPanel = new JPanel();
                    singleCarPanel.setLayout(new BoxLayout(singleCarPanel, BoxLayout.X_AXIS)); 

                    // Displays the car information
                    singleCarPanel.add(new JLabel(car));

                    // Create the Accept and Reject buttons
                    JButton acceptButton = new JButton("Accept");
                    JButton rejectButton = new JButton("Reject");

                  
                    acceptButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Writes Accepted Cars to New Text File
                            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            try (FileWriter writer = new FileWriter("accepted_cars.txt", true)) {
                                writer.write("Accepted Car: " + car + "\n");
                                writer.write("Timestamp: " + timestamp + "\n");
                                writer.write("---------------------------\n");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            
                            JOptionPane.showMessageDialog(frame, "Car Accepted: " + car, "Car Status", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });

                    
                    rejectButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Removes Rejected cars from the Car Submission List
                        	carOwnerSubmissions.remove(index);
                            JOptionPane.showMessageDialog(frame, "Car has been rejected: " + car, "Car Status", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });

                  
                    singleCarPanel.add(acceptButton);
                    singleCarPanel.add(rejectButton);

                    
                    carPanel.add(singleCarPanel);
                }

                
                JOptionPane.showMessageDialog(frame, carPanel, "Job Submissions", JOptionPane.INFORMATION_MESSAGE);
            }
        });





        // create account
        JPanel createAccountPanel = new JPanel(new GridLayout(13,2));
        createAccountPanel.setVisible(false);

        JRadioButton JobSubmitterAccountRadioButton = new JRadioButton("Job Submitter");
        JRadioButton CarOwnerAccountRadioButton = new JRadioButton("Car Owner");

        ButtonGroup selectNewAccountType = new ButtonGroup();
        selectNewAccountType.add(JobSubmitterAccountRadioButton);
        selectNewAccountType.add(CarOwnerAccountRadioButton);
        createAccountPanel.add(JobSubmitterAccountRadioButton);
        createAccountPanel.add(CarOwnerAccountRadioButton);
        JobSubmitterAccountRadioButton.setSelected(true);

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
        carModelCreateLabel.setVisible(false);
        carModelCreateTextField.setVisible(false);

        JLabel carMakeCreateLabel = new JLabel("car make: ");
        JTextField carMakeCreateTextField = new JTextField(30);

        createAccountPanel.add(carMakeCreateLabel);
        createAccountPanel.add(carMakeCreateTextField);
        carMakeCreateLabel.setVisible(false);
        carMakeCreateTextField.setVisible(false);

        JLabel carYearCreateLabel = new JLabel("car year: ");
        JTextField carYearCreateTextField = new JTextField(30);

        createAccountPanel.add(carYearCreateLabel);
        createAccountPanel.add(carYearCreateTextField);
        carYearCreateLabel.setVisible(false);
        carYearCreateTextField.setVisible(false);

        JLabel carVinCreateLabel = new JLabel("car vin: ");
        JTextField carVinCreateTextField = new JTextField(30);

        createAccountPanel.add(carVinCreateLabel);
        createAccountPanel.add(carVinCreateTextField);
        carVinCreateLabel.setVisible(false);
        carVinCreateTextField.setVisible(false);

        JLabel carPlateNumberCreateLabel = new JLabel("car plate number: ");
        JTextField carPlateNumberCreateTextField = new JTextField(30);

        createAccountPanel.add(carPlateNumberCreateLabel);
        createAccountPanel.add(carPlateNumberCreateTextField);
        carPlateNumberCreateLabel.setVisible(false);
        carPlateNumberCreateTextField.setVisible(false);

        JLabel carTypeCreateLabel = new JLabel("car type: ");
        JTextField carTypeCreateTextField = new JTextField(30);

        createAccountPanel.add(carTypeCreateLabel);
        createAccountPanel.add(carTypeCreateTextField);
        carTypeCreateLabel.setVisible(false);
        carTypeCreateTextField.setVisible(false);


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
        clientCarOwnerCreationButton.setVisible(false);

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

        JobSubmitterAccountRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carModelCreateLabel.setVisible(false);
                carModelCreateTextField.setVisible(false);
                carMakeCreateTextField.setVisible(false);
                carMakeCreateLabel.setVisible(false);
                carYearCreateLabel.setVisible(false);
                carYearCreateTextField.setVisible(false);
                carVinCreateLabel.setVisible(false);
                carVinCreateTextField.setVisible(false);
                carPlateNumberCreateLabel.setVisible(false);
                carPlateNumberCreateTextField.setVisible(false);
                carTypeCreateLabel.setVisible(false);
                carTypeCreateTextField.setVisible(false);
                clientCarOwnerCreationButton.setVisible(false);
                clientJobSubmitterCreationButton.setVisible(true);
            }
        });

        CarOwnerAccountRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carModelCreateLabel.setVisible(true);
                carModelCreateTextField.setVisible(true);
                carMakeCreateTextField.setVisible(true);
                carMakeCreateLabel.setVisible(true);
                carYearCreateLabel.setVisible(true);
                carYearCreateTextField.setVisible(true);
                carVinCreateLabel.setVisible(true);
                carVinCreateTextField.setVisible(true);
                carPlateNumberCreateLabel.setVisible(true);
                carPlateNumberCreateTextField.setVisible(true);
                carTypeCreateLabel.setVisible(true);
                carTypeCreateTextField.setVisible(true);
                clientCarOwnerCreationButton.setVisible(true);
                clientJobSubmitterCreationButton.setVisible(false);
            }
        });

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

        //cloud controller log out
        JButton cloudControllerLogoutButton = new JButton("Log Out");
        cloudControllerPanel.add(cloudControllerLogoutButton);

        cloudControllerLogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePagePanel.setVisible(true);
                cloudControllerPanel.setVisible(false);
                currentSelectionButton.setText("");
            }
        });

        frame.add(logInPanel);
        frame.add(createAccountPanel);

        frame.add(informationInputJobSubmitterPanel);
        frame.add(informationInputCarOwnerPanel);
        frame.add(clientProfilePanel);
        frame.add(ownerProfilePanel);
        frame.add(cloudControllerPanel);

        frame.setVisible(true);

        System.out.println("Hello world!");
    }
}
