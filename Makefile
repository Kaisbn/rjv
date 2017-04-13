SLIDES_DIR = slides
SUBJECT_DIR = subjects
SLIDES_PDF = slides.pdf
SUBJECT_PDF = subject.pdf

all:: slides subject given-files

slides:
	${MAKE} -C ${SLIDES_DIR} all

subject:
	${MAKE} -C ${SUBJECT_DIR} all

given-files:
	tar -jcvf files/creeps.tar.bz2 -C files ./creeps

clean:
	${MAKE} -C ${SLIDES_DIR} clean
	${MAKE} -C ${SUBJECT_DIR} clean
	${RM} ${SLIDES_PDF}
	${RM} ${SUBJECT_PDF}
	${RM} files/creeps.tar.bz2

.PHONY: slides subject tarball
