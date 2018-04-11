# Local DNS for Client Application
Class `ResourceRecord` stores psuedo resource record objects.  

By default, `local-dns` contains the following Resource Records:  
- (herCDN.com, ns.herCDN.com, NS) : *redirect TLD herCDN.com to ns.herCDN.com*
- (ns.herCDN.com, xxx.xxx.xxx.xxx, A) : *IP for ns.herCDN.com*  
- (hisCinema.com, ns.hisCinema.com, NS) : *redirect TLD hisCinema.com to ns.hisCinema.com*
- (ns.hisCinema.com, xxx.xxx.xxx.xxx, A) : *IP for ns.hisCinema.com*

## Set up
The following variables should be set:
- IP for name-server/machine running **ns.herCDN.com**
- Port for name-server/machine running **ns.herCDN.com**
- IP for name-server/machine running **ns.hisCinema.com**
- Port for name-server/machine running **ns.hisCinema.com**
- Port for name-server/machine running **Local DNS**

Set the two lines accordingly:
```
public static final String  HIS_CINEMA_NS_IP    = "xxx.xxx.xxx.xxx";
public static final int     HIS_CINEMA_NS_PORT  = xxxxx;
public static final String  HER_CDN_NS_IP       = "xxx.xxx.xxx.xxx";
public static final int     HER_CDN_NS_PORT     = xxxxx;
public static final int     LOCAL_DNS_LISTENING_PORT = xxxxx;
```

## Run
To run
```
javac LocalDNS.java
java LocalDNS
```
