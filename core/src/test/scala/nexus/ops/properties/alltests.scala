package nexus.ops.properties

import nexus.impl.jvm._
import nexus.prob._

class OpSSFloatTests extends OpSSTests(Normal(0f, 10f))
class OpSSDoubleTests extends OpSSTests(Normal(0d, 10d))

class OpSSSFloatTests extends OpSSSTests(Normal(0f, 10f))
class OpSSSDoubleTests extends OpSSSTests(Normal(0d, 10d))

class OpVSFloatTests extends OpVSTests(Normal(0f, 10f))

class OpVVFloatTests extends OpVVTests(Normal(0f, 10f))
