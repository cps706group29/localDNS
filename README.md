# `Local DNS` for Client

By default, `Local DNS` contains the following Resource Records:  

| Resource Record | Use |
| - | - |
| (herCDN.com, ns.herCDN.com, NS) | Translate `herCDN.com` to `ns.herCDN.com` |
| (ns.herCDN.com, xxx.xxx.xxx.xxx, A) | IP for `ns.herCDN.com` |
| (hisCinema.com, ns.hisCinema.com, NS) | Translate `hisCinema.com` to `ns.hisCinema.com` |
| (ns.hisCinema.com, xxx.xxx.xxx.xxx, A) | IP for `ns.hisCinema.com` |

## Compile and Run
```
javac LocalDNS.java
java LocalDNS
```
Set following variables after running the program:
- **IP** and **PORT** for the name-server/machine running `ns.herCDN.com`
- **IP** and **PORT** for the name-server/machine running `ns.hisCinema.com`
- **PORT** for the name-server/machine running the `Local DNS`
