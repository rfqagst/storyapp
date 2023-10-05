# CITIZEN Story APP

An innovative application built with a robust MVVM architecture, providing users with a seamless experience for sharing and viewing stories.
<p align="center">
  <img src="https://i.imgur.com/cZbOfwf.png" width="30%" style="margin-right:20px;" />
  <img src="https://i.imgur.com/SUU03fm.png" width="30%" />
</p>

## Features

### 🏛 **Architecture**
<ul>
<li>Implemented <strong>MVVM</strong> architecture using:</li>
  <ul>
    <li>ViewModel</li>
    <li>LiveData</li>
    <li>Repository</li>
  </ul>
</ul>

### 🔐 **Authentication**
<ul>
  <li>Email and password validation using custom views.</li>
</ul>

### 📖 **Story**
<ul>
  <li><strong>Paging List</strong> feature to display stories.</li>
  <li>Fetch and display stories from <strong>Dicoding API</strong>:</li>
  <ul>
    <li>Show list of stories with details: Name, Image, and Description.</li>
  </ul>
  <li><strong>Post a story</strong>:</li>
  <ul>
    <li>Upload a photo from the gallery or directly from the camera.</li>
    <li>Add a description to the story.</li>
    <li>Share the story along with the user's location.</li>
  </ul>
</ul>

### 🎥 **Animation**
<ul>
  <li>Property animation triggered when a user clicks on a story from the list.</li>
</ul>

### 🎴 **Stack Widget**
<ul>
  <li>Display a stack widget of stories.</li>
  <li>Directly navigate to the story details when the widget is clicked.</li>
</ul>

### 🌍 **Localization**
<ul>
  <li>Supports both <strong>Indonesian</strong> and <strong>English</strong> languages.</li>
</ul>

### 🗺 **Maps**
<ul>
  <li>Display the location of stories.</li>
  <li>Feature to change the map's style.</li>
</ul>

### 🔍 **Testing**
#### Unit Tests:
<ul>
  <li>Ensure that fetching a story returns non-null data.</li>
  <li>Check for empty quotes and ensure they return "No Data" message.</li>
</ul>

#### UI Tests:
<ul>
  <li><strong>Login</strong>:</li>
  <ul>
    <li>Successful login.</li>
    <li>Login with incorrect email.</li>
    <li>Login with incorrect password.</li>
  </ul>
  <li><strong>Logout</strong>:</li>
  <ul>
    <li>Successful logout.</li>
    <li>Ensure redirection to the login page after logout.</li>
  </ul>
</ul>
