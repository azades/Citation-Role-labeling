Citation Role labeling
======================

Citation relationship between scientific publications has been successfully used for bibliometrics, information retrieval
and data mining tasks, and citation-based recommendation algorithms are well documented. While previous studies 
investigated citation relations from various viewpoints, most of them share the same assumption that, if paper1 cites 
paper2 (or author1 cites author2), they are connected, regardless of citation importance, sentiment, reason, topic, or 
motivation. However, this assumption is oversimplified. In this study, we propose a novel method to automatically label
the massive citations in the scientific repository, a.k.a. citation role labeling, by employing citing and cited paper
context. Unlike earlier studies, we employed both local features, i.e., citation textual context, pairwise features, 
i.e., citing and cited paper similarities, and global features, i.e., citing and cited paper proximity on the
heterogeneous graph. Evaluation result shows pairwise and global features, if properly used, can be very helpful to
enhance the citation role labeling performance, especially while full-text data is not readily available.
