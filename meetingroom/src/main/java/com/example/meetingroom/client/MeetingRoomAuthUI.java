package com.example.meetingroom.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MeetingRoomAuthUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;
    private String currentSessionCookie = null;

    public MeetingRoomAuthUI() {
        setTitle("Meeting Room Authentication");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.add(new JLabel("Username: "));
        usernameField = new JTextField(20);
        usernamePanel.add(usernameField);

        // Password
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.add(new JLabel("Password:  "));
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Status Label
        statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Who Am I Button
        JButton whoAmIButton = new JButton("Who Am I?");
        whoAmIButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to main panel
        mainPanel.add(usernamePanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(whoAmIButton);
        mainPanel.add(logoutButton);

        add(mainPanel);

        // Login Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                try {
                    String response = sendPostRequest("/auth/login", 
                        "username=" + username + "&password=" + password);
                    statusLabel.setForeground(Color.GREEN);
                    statusLabel.setText("Login Successful!");
                } catch (Exception ex) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setText("Login Failed: " + ex.getMessage());
                }
            }
        });

        // Register Action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                try {
                    String response = sendPostRequest("/auth/register", 
                        "username=" + username + "&password=" + password);
                    statusLabel.setForeground(Color.GREEN);
                    statusLabel.setText("Registration Successful!");
                } catch (Exception ex) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setText("Registration Failed: " + ex.getMessage());
                }
            }
        });

        // Who Am I Action
        whoAmIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String response = sendGetRequest("/auth/whoami");
                    statusLabel.setForeground(Color.GREEN);
                    statusLabel.setText("Logged in as: " + response);
                } catch (Exception ex) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setText("No user logged in");
                }
            }
        });

        // Logout Action
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String response = sendPostRequest("/auth/logout", "");
                    statusLabel.setForeground(Color.GREEN);
                    statusLabel.setText("Logged out successfully!");
                    currentSessionCookie = null;
                } catch (Exception ex) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setText("Logout Failed: " + ex.getMessage());
                }
            }
        });
    }

    private String sendPostRequest(String endpoint, String postData) throws Exception {
        URL url = new URL("http://localhost:8080" + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        // Add session cookie if exists
        if (currentSessionCookie != null) {
            conn.setRequestProperty("Cookie", currentSessionCookie);
        }

        try (OutputStream os = conn.getOutputStream()) {
            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
            os.write(postDataBytes);
        }

        // Check for session cookie in response
        String cookieHeader = conn.getHeaderField("Set-Cookie");
        if (cookieHeader != null) {
            currentSessionCookie = cookieHeader.split(";")[0];
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new RuntimeException("HTTP error code : " + responseCode);
        }
    }

    private String sendGetRequest(String endpoint) throws Exception {
        URL url = new URL("http://localhost:8080" + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("GET");

        // Add session cookie if exists
        if (currentSessionCookie != null) {
            conn.setRequestProperty("Cookie", currentSessionCookie);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new RuntimeException("HTTP error code : " + responseCode);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MeetingRoomAuthUI().setVisible(true);
        });
    }
}