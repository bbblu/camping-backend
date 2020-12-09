function logout(loginUrl) {
    document.cookie = "X-Auth-Token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
    location.href = loginUrl;
}
