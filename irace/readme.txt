sh target.sh testConfig2 instance1 1234567 instances\\A-n32-k5.vrp.txt --selector-standard --alpha 4.34 --beta 4.63 --candidate-yes --candidate-ratio 0.82 --rank-based-ant-s --local-update-yes --standard --ant-num 5 --runtime 1000 --lupd-epsilon 0.82 --tau0 0.84 --local-search-none --evaporation-remains 0.62 --ras-w 26 --ras-bounded-no --ras-k 1.78 --reinitialization-yes --reinitialization-time 797 --end

java -jar vrp.jar testConfig2 instance1 1234567 instances/A-n32-k5.vrp.txt --selector-standard --alpha 4.34 --beta 4.63 --candidate-yes --candidate-ratio 0.82 --rank-based-ant-s --local-update-yes --standard --ant-num 5 --runtime 1000 --lupd-epsilon 0.82 --tau0 0.84 --local-search-none --evaporation-remains 0.62 --ras-w 26 --ras-bounded-no --ras-k 1.78 --reinitialization-yes --reinitialization-time 797 --end


./target.sh testConfig2 instance1 1234567 instances/A-n32-k5.vrp.txt --selector-standard --alpha 4.34 --beta 4.63 --candidate-yes --candidate-ratio 0.82 --rank-based-ant-s --local-update-yes --standard --ant-num 5 --runtime 1000 --lupd-epsilon 0.82 --tau0 0.84 --local-search-none --evaporation-remains 0.62 --ras-w 26 --ras-bounded-no --ras-k 1.78 --reinitialization-yes --reinitialization-time 797 --end


Java 1.8.0_43



ssh asaranov@majorana.ulb.ac.be
password

exit ssh:
ctrl+D






--------- PATHS -------------------

get full current directory:
echo $PWD



get current PATH variable:
echo $PATH





--------- SH ----------------------

Must start as:
#!/bin/bash
#$ -N %NAME_OF_JOB%
#$ -cwd


to debug a shell cripte
sh -x script [arg1 ...]


make file executable (for example for running *.sh):
chmod +x [filename]


add a job:
qsub [*.sh]

status of my jobs:
qstat

delete a job:
qdel [ID]


download:
scp asaranov@majorana.ulb.ac.be:remote_file local_file 

upload:
scp [local_file] asaranov@majorana.ulb.ac.be:[remote_file]

scp vrp.jar asaranov@majorana.ulb.ac.be:vrp.jar


unzip everything into the current folder (does not create new):
unzip [file]



--------- R -----------------------

Start R:
R
not "r"!!!