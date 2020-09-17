# Data anonymization in DICOM files

Simple Java application to anonymization of data in DICOM files


##### Confidential data that is anonymized:
- (0010,0010)	PN	Patient's Name
- (0010,0030)	DA	Patient's Birth Date
- (0010,0032)	TM	Patient's Birth Time
- (0010,0033)	LO	Patient's Birth Date in Alternative Calendar
- (0010,0034)	LO	Patient's Death Date in Alternative Calendar
- (0010,0040)	CS	Patient's Sex
- (0010,1001)	PN	Other Patient Names
- (0010,1005)	PN	Patient's Birth Name
- (0010,1010)	AS	Patient's Age
- (0010,1020)	DS	Patient's Size
- (0010,1030)	DS	Patient's Weight
- (0010,1040)	LO	Patient's Address
- (0010,1060)	PN	Patient's Mother's Birth Name
- (0010,2154)	SH	Patient's Telephone Numbers
- (0010,2155)	LT	Patient's Telecom Information
- (0010,21F0)	LO	Patient's Religious Preference
- (0010,2203)	CS	Patient's Sex Neutered
- (0038,0300)	LO	Current Patient Location
- (0038,0400)	LO	Patient's Institution Residence

---
Copyright 2020 © Kinga Pawłowska