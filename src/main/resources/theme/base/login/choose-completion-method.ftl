<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <title>Choose Completion Method</title>
</head>
<body>
    <h1>Choose Completion Method</h1>
    <form action="${url.loginAction}" method="post">
        <label for="email">Complete Registration via MAGIC LINK</label>
        <input type="radio" id="magic-link" name="completion_method" value="magic-link">
        <br>
        <label for="webauthn">Complete Registration via WebAuthn</label>
        <input type="radio" id="webauthn" name="completion_method" value="webauthn">
        <br><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
