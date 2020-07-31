#!groovy

import jenkins.model.*
import hudson.security.*
import jenkins.security.s2m.AdminWhitelistRule
import org.jenkinsci.plugins.*

def instance = Jenkins.getInstance()

// def user = new File("/run/secrets/jenkins-user").text.trim()
// def pass = new File("/run/secrets/jenkins-pass").text.trim()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin", "p@ss123")
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()

Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)


String server = 'ldap://ldap.test.com'
String rootDN = 'dc=test,dc=com'
String userSearchBase = 'ou=users'
String userSearch = ''
String groupSearchBase = 'ou=groups'
String managerDN = 'uid=svc_jenkins,ou=users,dc=test,dc=com'
String managerPassword = 'password'
boolean inhibitInferRootDN = false
    
SecurityRealm ldap_realm = new LDAPSecurityRealm(server, rootDN, userSearchBase, userSearch, groupSearchBase, managerDN, managerPassword, inhibitInferRootDN) 
Jenkins.instance.setSecurityRealm(ldap_realm)
Jenkins.instance.save()
