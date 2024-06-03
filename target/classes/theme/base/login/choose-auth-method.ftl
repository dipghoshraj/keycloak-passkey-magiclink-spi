<form id="kc-select-auth-method-form" action="${url.loginAction}" method="post">
    <div>
        <label>
            <input type="radio" name="auth_method" value="webauthn" /> Register with WebAuthn
        </label>
    </div>
    <div>
        <label>
            <input type="radio" name="auth_method" value="otp" /> Send OTP
        </label>
    </div>
    <div>
        <button type="submit">Submit</button>
    </div>
</form>
